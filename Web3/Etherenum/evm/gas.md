## Gas计算

Transaction Fee = Gas Price * Usage by Txn

Gas Price = Max(Base + Max Priority（基础费用+小费）, Max（最大限制）) 

Burnt = Base * Usage by Txn

Txn Savings = (Max - Gas Price) * Usage by Txn

```
假设 Jordan 需要向 Taylor 支付 1 ETH。 在交易中，矿工报酬限额为 21,000 单位，基本费用的价格是 100 gwei。 Jordan 支付了 10 gwei 作为小费。

使用上面的公式，我们可以计算 21,000 * (100 + 10) = 2,310,000 gwei 或 0.00231 ETH。

当 Jordan 发送钱时，将从 Jordan 账户中扣除 1.00231 ETH。 Taylor 将获得 1.0000 ETH。 矿工得到 0.00021 ETH。 0.0021 ETH 的基本费用被燃烧。
```
Gas Used值解释:

The base fee is calculated by a formula that compares the size of the previous block (the amount of gas used for all the transactions) with the target size. The base fee will increase by a maximum of 12.5% per block if the target block size is exceeded.

大致上就是使用Gas占比所设定的Gas费，这个值越高，说明负载越高。

## 区块limit

区块limit和交易limit不同，指一个区块的最大gas。

london升级之前区块费用固定，升级之后可变，基本大小为1500万gas，最大限制为3000wgas

## Gas use计算

由EVM对操作指令逐个处理，每个指令都有对应的Gas

https://ethereum.org/en/developers/docs/evm/opcodes/

最终Gasused为所有指令消耗相加，所有手续费为**gasPrice * gasUsed。**

如果交易未完成，所需的费用超过了gas limit，将出现Out of Gas，然后回滚状态，已消耗的gas不会返回。