```go
blockchain.go

func (bc *BlockChain) writeBlockAndSetHead(block *types.Block, receipts []*types.Receipt, logs []*types.Log, state *state.StateDB, emitHeadEvent bool) (status WriteStatus, err error) {
	if err := bc.writeBlockWithState(block, receipts, logs, state); err != nil {
		return NonStatTy, err
	}
	currentBlock := bc.CurrentBlock()
	//1.判断是否需要重构链
	reorg, err := bc.forker.ReorgNeeded(currentBlock.Header(), block.Header())
	if err != nil {
		return NonStatTy, err
	}
	if reorg {
		// Reorganise the chain if the parent is not the head block
		if block.ParentHash() != currentBlock.Hash() {
			//2.重构链
			if err := bc.reorg(currentBlock, block); err != nil {
				return NonStatTy, err
			}
		}
		status = CanonStatTy
	} else {//发现自己是测链
		status = SideStatTy
	}
	// Set new head.
	if status == CanonStatTy {
		bc.writeHeadBlock(block)
	}
	bc.futureBlocks.Remove(block.Hash())

	//3.发送主链或测链信号
	if status == CanonStatTy {
		bc.chainFeed.Send(ChainEvent{Block: block, Hash: block.Hash(), Logs: logs})
		if len(logs) > 0 {
			bc.logsFeed.Send(logs)
		}
		// In theory we should fire a ChainHeadEvent when we inject
		// a canonical block, but sometimes we can insert a batch of
		// canonicial blocks. Avoid firing too many ChainHeadEvents,
		// we will fire an accumulated ChainHeadEvent and disable fire
		// event here.
		if emitHeadEvent {
			bc.chainHeadFeed.Send(ChainHeadEvent{Block: block})
		}
	} else {
		bc.chainSideFeed.Send(ChainSideEvent{Block: block})
	}
	return status, nil
}
```

### ReorgNeeded

```go
func (f *ForkChoice) ReorgNeeded(current *types.Header, header *types.Header) (bool, error) {
	var (
		//1.获得将要插入的block和当前链头的block的total difficulty
		//total difficulty表示某个链当前所有区块的难度总和，其可以视为链切换的指示，总是难度更大的链得到认可
		localTD  = f.chain.GetTd(current.Hash(), current.Number.Uint64())
		externTd = f.chain.GetTd(header.Hash(), header.Number.Uint64())
	)
	if localTD == nil || externTd == nil {
		return false, errors.New("missing td")
	}
	// Accept the new header as the chain head if the transition
	// is already triggered. We assume all the headers after the
	// transition come from the trusted consensus layer.
	if ttd := f.chain.Config().TerminalTotalDifficulty; ttd != nil && ttd.Cmp(externTd) <= 0 {
		return true, nil
	}
	// If the total difficulty is higher than our known, add it to the canonical chain
	// Second clause in the if statement reduces the vulnerability to selfish mining.
	// Please refer to http://www.cs.cornell.edu/~ie53/publications/btcProcFC.pdf
	//2.比较难度，externTd > localTD时就可能发生链切换
	reorg := externTd.Cmp(localTD) > 0
	if !reorg && externTd.Cmp(localTD) == 0 {
		number, headNumber := header.Number.Uint64(), current.Number.Uint64()
		//2.1如果难度相等，比较blocknumber，较小的为主链
		if number < headNumber {
			reorg = true
		} else if number == headNumber {
			//2.2如果高度也相等，preserve函数更倾向于自己挖出的区块为主链
			var currentPreserve, externPreserve bool
			if f.preserve != nil {
				currentPreserve, externPreserve = f.preserve(current), f.preserve(header)
			}
			reorg = !currentPreserve && (externPreserve || f.rand.Float64() < 0.5)
		}
	}
	return reorg, nil
}
```

## reorg

```go
func (bc *BlockChain) reorg(oldBlock, newBlock *types.Block) error {
	var (
		newChain    types.Blocks
		oldChain    types.Blocks
		commonBlock *types.Block

		deletedTxs []common.Hash
		addedTxs   []common.Hash

		deletedLogs [][]*types.Log
		rebirthLogs [][]*types.Log
	)
	// Reduce the longer chain to the same number as the shorter one
	//1.1 当前链比新的链长，当前链高度超过新链的部分，事务加入deletedTxs，准备回滚
	if oldBlock.NumberU64() > newBlock.NumberU64() {
		// Old chain is longer, gather all transactions and logs as deleted ones
		for ; oldBlock != nil && oldBlock.NumberU64() != newBlock.NumberU64(); oldBlock = bc.GetBlock(oldBlock.ParentHash(), oldBlock.NumberU64()-1) {
			oldChain = append(oldChain, oldBlock)
			for _, tx := range oldBlock.Transactions() {
				deletedTxs = append(deletedTxs, tx.Hash())
			}

			// Collect deleted logs for notification
			logs := bc.collectLogs(oldBlock.Hash(), true)
			if len(logs) > 0 {
				deletedLogs = append(deletedLogs, logs)
			}
		}
		//1.2 新的链更长，新的链超过的部分加入newChain
	} else {
		// New chain is longer, stash all blocks away for subsequent insertion
		for ; newBlock != nil && newBlock.NumberU64() != oldBlock.NumberU64(); newBlock = bc.GetBlock(newBlock.ParentHash(), newBlock.NumberU64()-1) {
			newChain = append(newChain, newBlock)
		}
	}
	
	if oldBlock == nil {
		return fmt.Errorf("invalid old chain")
	}
	if newBlock == nil {
		return fmt.Errorf("invalid new chain")
	}
	
	//2.此时oldChain和newChain的高度一致，开始寻找公共祖先，当前链的部分加入deletedTxs，新的链加入newChain
	for {
		// If the common ancestor was found, bail out
		if oldBlock.Hash() == newBlock.Hash() {
			commonBlock = oldBlock
			break
		}
		// Remove an old block as well as stash away a new block
		oldChain = append(oldChain, oldBlock)
		for _, tx := range oldBlock.Transactions() {
			deletedTxs = append(deletedTxs, tx.Hash())
		}

		// Collect deleted logs for notification
		logs := bc.collectLogs(oldBlock.Hash(), true)
		if len(logs) > 0 {
			deletedLogs = append(deletedLogs, logs)
		}
		newChain = append(newChain, newBlock)

		// Step back with both chains
		oldBlock = bc.GetBlock(oldBlock.ParentHash(), oldBlock.NumberU64()-1)
		if oldBlock == nil {
			return fmt.Errorf("invalid old chain")
		}
		newBlock = bc.GetBlock(newBlock.ParentHash(), newBlock.NumberU64()-1)
		if newBlock == nil {
			return fmt.Errorf("invalid new chain")
		}
	}

	// Ensure the user sees large reorgs
	if len(oldChain) > 0 && len(newChain) > 0 {
		logFn := log.Info
		msg := "Chain reorg detected"
		if len(oldChain) > 63 {
			msg = "Large chain reorg detected"
			logFn = log.Warn
		}
		logFn(msg, "number", commonBlock.Number(), "hash", commonBlock.Hash(),
			"drop", len(oldChain), "dropfrom", oldChain[0].Hash(), "add", len(newChain), "addfrom", newChain[0].Hash())
		blockReorgAddMeter.Mark(int64(len(newChain)))
		blockReorgDropMeter.Mark(int64(len(oldChain)))
		blockReorgMeter.Mark(1)
	} else if len(newChain) > 0 {
		// Special case happens in the post merge stage that current head is
		// the ancestor of new head while these two blocks are not consecutive
		log.Info("Extend chain", "add", len(newChain), "number", newChain[0].Number(), "hash", newChain[0].Hash())
		blockReorgAddMeter.Mark(int64(len(newChain)))
	} else {
		// len(newChain) == 0 && len(oldChain) > 0
		// rewind the canonical chain to a lower point.
		log.Error("Impossible reorg, please file an issue", "oldnum", oldBlock.Number(), "oldhash", oldBlock.Hash(), "oldblocks", len(oldChain), "newnum", newBlock.Number(), "newhash", newBlock.Hash(), "newblocks", len(newChain))
	}
	
	// taking care of the proper incremental order.
	//3.newChain为共识的最长链，开始提交，注意倒序的顺序（寻找时方向相反）
	for i := len(newChain) - 1; i >= 1; i-- {
		// Insert the block in the canonical way, re-writing history
		bc.writeHeadBlock(newChain[i])

		// Collect the new added transactions.
		for _, tx := range newChain[i].Transactions() {
			addedTxs = append(addedTxs, tx.Hash())
		}
	}

	//4.开始回滚事务
	indexesBatch := bc.db.NewBatch()
	for _, tx := range types.HashDifference(deletedTxs, addedTxs) {
		rawdb.DeleteTxLookupEntry(indexesBatch, tx)
	}
	// Delete any canonical number assignments above the new head
	number := bc.CurrentBlock().NumberU64()
	for i := number + 1; ; i++ {
		hash := rawdb.ReadCanonicalHash(bc.db, i)
		if hash == (common.Hash{}) {
			break
		}
		rawdb.DeleteCanonicalHash(indexesBatch, i)
	}
	if err := indexesBatch.Write(); err != nil {
		log.Crit("Failed to delete useless indexes", "err", err)
	}

	// Collect the logs
	for i := len(newChain) - 1; i >= 1; i-- {
		// Collect reborn logs due to chain reorg
		logs := bc.collectLogs(newChain[i].Hash(), false)
		if len(logs) > 0 {
			rebirthLogs = append(rebirthLogs, logs)
		}
	}
	// If any logs need to be fired, do it now. In theory we could avoid creating
	// this goroutine if there are no events to fire, but realistcally that only
	// ever happens if we're reorging empty blocks, which will only happen on idle
	// networks where performance is not an issue either way.
	if len(deletedLogs) > 0 {
		bc.rmLogsFeed.Send(RemovedLogsEvent{mergeLogs(deletedLogs, true)})
	}
	if len(rebirthLogs) > 0 {
		bc.logsFeed.Send(mergeLogs(rebirthLogs, false))
	}
	//5.这里把被回滚的区块作为叔块，发送测链信号
	if len(oldChain) > 0 {
		for i := len(oldChain) - 1; i >= 0; i-- {
			bc.chainSideFeed.Send(ChainSideEvent{Block: oldChain[i]})
		}
	}
	return nil
}
```

## 叔块

以太坊出块间隔平均为12秒，区块链软分叉是一种普遍现象，如果采取和比特币一样处理方式，只有最长链上的区块才有出块奖励，对于那些挖到区块而最终不在最长链上的矿工来说，就很不公平，而且这种“不公平”将是一个普遍情况。这会影响矿工们挖矿的积极性，甚至可能削弱以太坊网络的系统安全，也是对算力的一种浪费。因此，以太坊系统对不在最长链上的叔块，设置了**叔块奖励**。

叔块奖励也分成两部分：**奖励叔块的创建者**和 **奖励收集叔块的矿工**。注意叔块是没有矿工费奖励的，叔块不保存事务

- 叔块的创建者：叔块创建者的奖励根据“近远”关系而不同，和当前区块隔得越远，奖励越少。最多七代，第一代为7/8区块奖励，第七代为1/8区块奖励。
- 叔块的收集者：每收录一个叔块固定为 1/32 的区块挖矿奖励

叔块的感知都是在出现链冲突时，然后发送叔块信号，接受端将在构建区块时加入叔块信息。

- 叔块信号发送1

```go
blockchain.go

func (bc *BlockChain) writeBlockAndSetHead(block *types.Block, receipts []*types.Receipt, logs []*types.Log, state *state.StateDB, emitHeadEvent bool) (status WriteStatus, err error) {
	...
	reorg, err := bc.forker.ReorgNeeded(currentBlock.Header(), block.Header())
	...
	if reorg {
		...
	} else {//不需要构建链，因为新区块total difficult小于当前
		status = SideStatTy
	}
	...

	if status == CanonStatTy {
		...
	} else {//当前区块为叔块，发送信号
		bc.chainSideFeed.Send(ChainSideEvent{Block: block})
	}
	return status, nil
}
```

- 叔块信号发送2

```go
func (bc *BlockChain) reorg(oldBlock, newBlock *types.Block) error {
	...
	if len(oldChain) > 0 {
		for i := len(oldChain) - 1; i >= 0; i-- {
			//所有被回滚的区块被视为叔块发送信号
			bc.chainSideFeed.Send(ChainSideEvent{Block: oldChain[i]})
		}
	}
	return nil
}
```

- 接受信号

```go
worker.go

func (w *worker) mainLoop() {
	...
	for {
		select {
		...

			//出现测链信号，尝试处理叔块信息
		case ev := <-w.chainSideCh:
			//处理重复
			if _, exist := w.localUncles[ev.Block.Hash()]; exist {
				continue
			}
			if _, exist := w.remoteUncles[ev.Block.Hash()]; exist {
				continue
			}
			//确定本地和外部
			if w.isLocalBlock != nil && w.isLocalBlock(ev.Block.Header()) {
				w.localUncles[ev.Block.Hash()] = ev.Block
			} else {
				w.remoteUncles[ev.Block.Hash()] = ev.Block
			}
			// If our sealing block contains less than 2 uncle blocks,
			// add the new uncle block if valid and regenerate a new
			// sealing block for higher profit.
			//一个区块只能包含2个叔块
			if w.isRunning() && w.current != nil && len(w.current.uncles) < 2 {
				start := time.Now()
				//提交叔块信息
				if err := w.commitUncle(w.current, ev.Block.Header()); err == nil {
					//正式提交构建区块
					w.commit(w.current.copy(), nil, true, start)
				}
			}
			...
		}
		...
	}
}
```

- 叔块奖励

```go
worker.go.commit() -> consensus.go (ethash *Ethash)FinalizeAndAssemble()
-> Finalize() -> accumulateRewards()

func accumulateRewards(config *params.ChainConfig, state *state.StateDB, header *types.Header, uncles []*types.Header) {
	// Select the correct block reward based on chain progression
	//1.结算区块奖励，最开始是5ETH，Byzantium分叉后为3ETH，Constantinople分叉后为2ETH
	blockReward := FrontierBlockReward
	if config.IsByzantium(header.Number) {
		blockReward = ByzantiumBlockReward
	}
	if config.IsConstantinople(header.Number) {
		blockReward = ConstantinopleBlockReward
	}
	// Accumulate the rewards for the miner and any included uncles
	reward := new(big.Int).Set(blockReward)
	r := new(big.Int)
	//2.结算叔块奖励
	for _, uncle := range uncles {
		r.Add(uncle.Number, big8)
		r.Sub(r, header.Number)
		r.Mul(r, blockReward)
		r.Div(r, big8)
		//创建叔块者视距离而定，从1/8～7/8
		state.AddBalance(uncle.Coinbase, r)

		//收录者奖励为1/32
		r.Div(blockReward, big32)
		reward.Add(reward, r)
	}
	//3.直接加到账户余额
	state.AddBalance(header.Coinbase, reward)
}
```