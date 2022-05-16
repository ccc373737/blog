import smartpy as sp

class MyContract(sp.Contract):
    LEASE_TYPE = sp.TRecord(
        token_id = sp.TNat,
        days = sp.TNat
    )

    def __init__(self, admin):
        self.init_type(sp.TRecord(
            #管理员
            admin = sp.TAddress,

            #tokenid -> NFT信息
            token_metadata = sp.TMap(
                sp.TNat, 
                sp.TRecord(
                    name = sp.TString, 
                    symbol = sp.TString)),

            #tokenid -> owner address信息
            owner = sp.TMap(
                sp.TNat, 
                sp.TAddress),

            #tokenid -> 租赁信息
            lease_map = sp.TMap(
                sp.TNat, 
                sp.TRecord(
                    daily_fee = sp.TMutez, 
                    use_right_address = sp.TAddress,
                    vaild_time = sp.TTimestamp))))
        
        self.init(
            admin=admin,
            token_metadata=sp.map(),
            owner=sp.map(),
            lease_map=sp.map())

    def is_administrator(self):
        return sp.sender == self.data.admin

    def make_metadata(name, symbol):
        return sp.record(name = name, symbol = symbol)

    @sp.entry_point
    def mint(self, params):
        sp.verify(self.is_administrator(), message = "NO permit")

        self.data.token_metadata[params.token_id] = params.metadata
        self.data.owner[params.token_id] = params.address

    @sp.entry_point
    def publish_lease(self, lease):
        # owner可借出
        sp.verify(sp.sender == self.data.owner[lease.token_id], message = "NO permit")
        # 当前未借出
        sp.verify((~self.data.lease_map.contains(lease.token_id)) | 
                  (sp.now > self.data.lease_map[lease.token_id].vaild_time) , message = "lease... can't modify")

        self.data.lease_map[lease.token_id] = sp.record(
            daily_fee = lease.daily_fee,
            use_right_address = sp.sender,
            vaild_time = sp.now
        )
    
    @sp.entry_point
    def lease(self, lease):
        sp.set_type(lease, MyContract.LEASE_TYPE)

        sp.verify(sp.sender != self.data.owner[lease.token_id], message = "NOT self lease!")
        sp.verify(self.data.lease_map.contains(lease.token_id), message = "Not currently for lease!")
        sp.verify(sp.now <= self.data.lease_map[lease.token_id].vaild_time, message = "Already lease...")

        total = sp.local("total", self.data.lease_map[lease.token_id].daily_fee)
        # 日费 * 天数
        total.value = sp.mul(lease.days, total.value)

        sp.verify(sp.amount >= total.value, message = "insufficient fund...")
        # 转账租金
        sp.send(self.data.owner[lease.token_id], total.value)

        # 使用权转移
        self.data.lease_map[lease.token_id].use_right_address = sp.sender

        # 设置到期时间
        self.data.lease_map[lease.token_id].vaild_time = sp.now.add_days(sp.to_int(lease.days))

    '''
    # 关于状态设置一点感悟

    # 一个普通的有过期时间的租借状态值的修改，在web2通常思路中是使用定时器框架遍历租借表，判断时间并修改状态，
    # 即使在追求性能优化情况下，也是使用Lazy修改模式，在某一方请求数据时，顺便加一个状态修正，
    # 在类似上述方案中，写入操作是不可避免的，甚至会冗余和修改多份状态，其本质还是空间换时间的思想，追求搜索速度，
    # 而web3中最大的不同在于写入操作是及其昂贵的，这意味着要尽可能地减少写入操作，保证读操作的纯净性，并压缩一切可以压缩的状态，
    # 如在本个合约中，用时间去判断隐含的租借状态，并放弃维护一个租借map以减少写入操作，
    # 当然如此带来的副作用是读取某个地址所拥有的使用权时需要遍历全部数据，但在写入如此昂贵的情况下，一些反直觉的高复杂度操作可能是最优解。
    @sp.offchain_view()
    def get_owner(self):
        list = sp.TList(sp.TRecord(
            name = sp.TString, 
            symbol = sp.TString))

        sp.for key in self.data.owner.keys():
            sp.if (self.data.owner[key] == sp.sender):
                # How to push??????
                list.push(self.data.token_metadata[key])

        sp.result(list)

    @sp.offchain_view()
    def get_user_right(self):
        map = sp.TMap(
                sp.TNat, 
                sp.TRecord(
                    name = sp.TString, 
                    symbol = sp.TString))

        # NFT拥有者
        sp.for key in self.data.owner.keys():
            sp.if (self.data.owner[key] == sp.sender):
                map[key] = self.data.token_metadata[key]

        # 过滤已出租资产
        sp.for key in map.keys():
            sp.if (sp.now <= self.data.lease_map[key].vaild_time):
               del map[key]

        # 已租借并在租期内
        sp.for key in self.data.lease_map.keys():
            sp.if (self.data.lease_map[key].use_right_address == sp.sender &
                   sp.now <= self.data.lease_map[key].vaild_time)：
                map[key] = self.data.token_metadata[key]

        sp.result(map)
    '''   

@sp.add_test(name = "MyContract")
def test():
    scenario = sp.test_scenario()

    admin = sp.test_account("Administrator")

    contract = MyContract(admin.address)

    scenario += contract

    alice = sp.test_account("Alice")
    Bob = sp.test_account("Bob")

    tok0_md = MyContract.make_metadata(
            name = "The Token Zero",
            symbol= "TK0" )

    contract.mint(address = alice.address,
                            metadata = tok0_md,
                            token_id = 0).run(sender = admin)

    contract.publish_lease(token_id = 0, daily_fee = sp.mutez(1500000)).run(sender = alice)

    contract.lease(token_id = 0, days = 10).run(sender = Bob, amount = sp.mutez(58000000))

         
