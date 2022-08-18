这里解析从提交transaction → 区块打包生效的过程

- txpoo.go.add()

发送事务入队事件 `pool.queueTxEvent(tx)`

```go
func (pool *TxPool) queueTxEvent(tx *types.Transaction) {
   select {
   case pool.queueTxEventCh <- tx:
   case <-pool.reorgShutdownCh:
   }
}
```

- scheduleReorgLoop()

`queuedEvents` → `case tx := <-pool.queueTxEventCh:` → `go pool.runReorg(nextDone, reset, dirtyAccounts, queuedEvents)`

```go
//事务消息定义
var queuedEvents  = make(map[common.Address]*txSortedMap)

//传递新增事务消息，放入queuedEvents中
case tx := <-pool.queueTxEventCh:
   // Queue up the event, but don't schedule a reorg. It's up to the caller to
   // request one later if they want the events sent.
   addr, _ := types.Sender(pool.signer, tx)
   if _, ok := queuedEvents[addr]; !ok {
      queuedEvents[addr] = newTxSortedMap()
   }
   queuedEvents[addr].Put(tx)

if curDone == nil && launchNextRun {
			// Run the background reorg and announcements
			//处理事务消息
			go pool.runReorg(nextDone, reset, dirtyAccounts, queuedEvents)

			// Prepare everything for the next round of reorg
			curDone, nextDone = nextDone, make(chan struct{})
			launchNextRun = false

			reset, dirtyAccounts = nil, nil
			queuedEvents = make(map[common.Address]*txSortedMap)
		}
```

- runReorg()

```go
promoted := pool.promoteExecutables(promoteAddrs)

...
	for _, tx := range promoted {
		addr, _ := types.Sender(pool.signer, tx)
		if _, ok := events[addr]; !ok {
			events[addr] = newTxSortedMap()
		}
		events[addr].Put(tx)
	}

	if len(events) > 0 {
		var txs []*types.Transaction
		for _, set := range events {
			txs = append(txs, set.Flatten()...)
		}
		//发送事件，在worker.go.mianloop中接受
		pool.txFeed.Send(NewTxsEvent{txs})
	}
```

- worker.go.mianloop()

```go

// 订阅事件
worker.txsSub = eth.TxPool().SubscribeNewTxsEvent(worker.txsCh)

//接受交易事件，排序后将事务数据加入区块中
case ev := <-w.txsCh:
			// Apply transactions to the pending state if we're not sealing
			//
			// Note all transactions received may not be continuous with transactions
			// already included in the current sealing block. These transactions will
			// be automatically eliminated.
			if !w.isRunning() && w.current != nil { //当前尚未工作中
				// If block is already full, abort
				//gas用尽，当前block满
				if gp := w.current.gasPool; gp != nil && gp.Gas() < params.TxGas {
					continue
				}
				txs := make(map[common.Address]types.Transactions)
				for _, tx := range ev.Txs {
					acc, _ := types.Sender(w.current.signer, tx)
					txs[acc] = append(txs[acc], tx)
				}
				//根据交易价格和Nonce值排序，形成有序集合，内部用了堆排序，TxByPriceAndTime实现了sort接口
				txset := types.NewTransactionsByPriceAndNonce(w.current.signer, txs, w.current.header.BaseFee)
				tcount := w.current.tcount
				//按顺序提交事务
				w.commitTransactions(w.current, txset, nil)

				// Only update the snapshot if any new transactions were added
				// to the pending block
				if tcount != w.current.tcount {
					w.updateSnapshot(w.current)
				}
			} else {
				// Special case, if the consensus engine is 0 period clique(dev mode),
				// submit sealing work here since all empty submission will be rejected
				// by clique. Of course the advance sealing(empty submission) is disabled.
				if w.chainConfig.Clique != nil && w.chainConfig.Clique.Period == 0 {
					//重新开始工作
					w.commitWork(nil, true, time.Now().Unix())
				}
			}
			//交易标记增加
			atomic.AddInt32(&w.newTxs, int32(len(ev.Txs)))

//实际上是加入这两个结构中
txs      []*types.Transaction
receipts []*types.Receipt

func (w *worker) commitTransaction(env *environment, tx *types.Transaction) ([]*types.Log, error) {
	snap := env.state.Snapshot()

	//构建收据
	receipt, err := core.ApplyTransaction(w.chainConfig, w.chain, &env.coinbase, env.gasPool, env.state, env.header, tx, &env.header.GasUsed, *w.chain.GetVMConfig())
	if err != nil {
		//出现错误时使用快照回滚
		env.state.RevertToSnapshot(snap)
		return nil, err
	}
	//env中写入Transaction和receipt
	env.txs = append(env.txs, tx)
	env.receipts = append(env.receipts, receipt)

	return receipt.Logs, nil
}
```

- resultLoop() 如果计算成功，所有数据准备完毕，包括block body中的transactions，就进行持久化操作，至此事务就被永久存放在链上了。

```go
type Block struct {
	header       *Header
	uncles       []*Header
	transactions Transactions

	// caches
	hash atomic.Value
	size atomic.Value

	// Td is used by package core to store the total difficulty
	// of the chain up to and including the block.
	td *big.Int

	// These fields are used by package eth to track
	// inter-peer block relay.
	ReceivedAt   time.Time
	ReceivedFrom interface{}
}
```