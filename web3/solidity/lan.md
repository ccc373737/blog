1. 声明

// SPDX-License-Identifier: GPL-3.0

// SPDX-License-Identifier: Unlincese 非开源

1. 声明版本

pragma solidity >=0.7.0 <0.9.0;

1. 引入包

```python
import "remix_tests.sol"; // this import is automatically injected by Remix.
import "hardhat/console.sol";
//相对路径
import "../contracts/3_Ballot.sol";

```

1. 继承两种形式

```python
contract Base {
    uint x;
    constructor(uint _x) { x = _x; }
}

//1.基础中指定参数
contract Derived1 is Base(7) {
    constructor() {}
}

//2.构造函数设置继承头
contract Derived2 is Base {
    constructor(uint _y) Base(_y * _y) {}
}
```

1. **运算

```python
//发行1kw币
//10 ^ 18 如1Ether = 10 ^ 18Wei
_mint(msg.sender, 10000000 * 10 ** decimals())
```

1. 函数

```python
0.7.0之后函数可以在合约外部定义

function name(args...) pubic
- external只能外部访问
- internal只能内部访问或子类
- public 所有位置可见
- private 只能内部访问，子类无法继承

function f(uint a) private pure
- pure 纯函数 无法读取也无法修改变量
- view 只读函数 无法修改变量

function f(uint a) private pure returns(return1, return2...)
可以返回多个值

function receive() external payable {
	emit Received(msg.sender, msg.value);
}
出现接受转账行为，必须用payable修饰，一般肯定也是外部调用

function destroy() virtual public {
     if (msg.sender == owner) selfdestruct(owner);
 }
virtual 表示可以被子类覆写
```

7.modifier 复用一些判断条件

```python
//也可以是有参构造
modifier onlyOwner() {
        require(msg.sender == owner, "no permitt");
        _;
    }
    
//也可使多重mod
    function updateVersion(uint _version) public onlyOwner {
        version = _version;
    }
```

1. event 回调函数通知

```python
event Deposit(address _from, string _msg, uint256 _value);

function depost(string memory _msg) public payable {
    emit Deposit(msg.sender, _msg, msg.value);
}
```

1. 枚举

```python
//没有;
enum Status {OFF, SEND, FINISH}

//返回给外部时候是一个int值，从0开始按顺序
//OFF:0 SEND:1 FINISH:2
Status public status = Status.SEND

//恢复为默认值0
function reset() external {
	delete status;
}

//获取枚举key或value
function getKeyByValue(Status _status) pure external returns (string memory) {
        if (Status.OFF == _status) {
            return "OFF";
        } else if (Status.SEND == _status) {
            return "SEND";
        } 
        
        return "FINISH";
    }

    function getValueByKey(string memory _status) pure external returns (Status) {
        if (keccak256(bytes(_status)) == keccak256(bytes("OFF"))) {
            return Status.OFF;
        } else if (keccak256(bytes(_status)) == keccak256(bytes("SEND"))) {
            return Status.SEND;
        }

         return Status.FINISH;
    }
```

1. error处理
2. 地址

```python
//当前合约地址
function getAddress() public view returns(address) {
        return address(this);
    }

//call的地址
msg.sender

//创建智能合约的地址，需要在构造函数中赋值
uint public version;
constructor() ERC20("DogChain", "DOC") {
        owner = msg.sender;
    }

//地址余额
function getBalance() external view returns(uint, uint, uint) {
        uint contractBalance = address(this).balance;
        uint senderBalance = msg.sender.balance;
        uint ownerBalance = owner.balance;
        return (contractBalance, senderBalance, ownerBalance);
    }
```

- 合约地址
-
