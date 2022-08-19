1. 声明

// SPDX-License-Identifier: GPL-3.0

// SPDX-License-Identifier: Unlincese 非开源

2. 声明版本

pragma solidity >=0.7.0 <0.9.0;

3. function

```solidity
function (<parameter types>) {internal|external} [pure|constant|view|payable] [returns (<return types>)]

internal 只能在内部使用
external 只能外部发起调用
public 任何地方都可以调用

pure 纯函数，既不可以读取指，也不可以写入值
view 读函数，只能读取值，不能写
pure和view不会消耗gas

public（或 external）函数过去有额外两个成员：.gas(uint) 和 .value(uint) 在0.6.2中弃用了，在 0.8.0 中移除了。 用 {gas: ...} 和 {value: ...} 代替

 _to.call{value:msg.value, gas:500000}("");
```

4. 引入包

```solidity
import "remix_tests.sol"; // this import is automatically injected by Remix.
import "hardhat/console.sol";
//相对路径
import "../contracts/3_Ballot.sol";

```

5. 继承两种形式

```solidity
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

6. **运算 (^运算)

```solidity
//发行1kw币
//10 ^ 18 如1Ether = 10 ^ 18Wei
_mint(msg.sender, 10000000 * 10 ** decimals())
```

7. 函数

```solidity
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

8.modifier 复用一些判断条件

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Mod {
    uint public count = 3;

    modifier modTest(uint mul) {//相当于一个代理，前后都可以执行自定义功能
        count *= mul;
        _;
        count *= mul;
    }

    function mulCount(uint mul) public modTest(mul) {

    }
}
```

9. event 回调函数通知

```solidity
event Deposit(address _from, string _msg, uint256 _value);

function depost(string memory _msg) public payable {
    emit Deposit(msg.sender, _msg, msg.value);
}
```

10. 枚举

```solidity
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

11. 地址

```solidity
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

12. Constant和Immutable

Constant在高版本中被拆分为view和pure

constant变量需要在编译前就指定

immutable则在构造函数中指定，

一旦指定完成无法再修改。

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract constant1 {
    string constant name = "aaaa";
    uint immutable ttype;//immutable变量会比public变量节省更多的gas

    constructor() {
        ttype = 1;
    }

    //constant变量只能用pure修饰
    function getName() public pure returns (string memory) {
        return name;
    }

    //immutable变量只能用view修饰
    function getTtpye() public view returns (uint) {
        return ttype;
    }
}
```

13. mapping

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Mapping {
    mapping (address => uint) account;

    function get(address _addr) public view returns (uint) {
        return account[_addr];
    }

    function set(address _addr, uint _balance) public {
        account[_addr] = _balance;
    }

    function remove(address _addr) public {
        delete account[_addr];
    }
}
```

14. Array

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Array {
    uint[] A;

    uint[3] B;

    uint[] C = [1,2,3,4,5];

		uint256[] memory bidPriceList = new uint256[](bidList.length);

    function getLen() public view returns (uint, uint, uint) {
        return (A.length, B.length, C.length);
    }

    function getIndex(uint index) public view returns (uint, uint, uint) {
        return (A[index], B[index], C[index]);
    }

    function push(uint num) public {
        A.push(num);
        //B.push(num);push不能用于定长数组
        C.push(num);
    }

    function pop() public {
        A.pop();//默认删除最后一个
    }

    function del(uint index) public {
        delete A[index];//将某个位置置为该类型默认置，uint就是0
    }

    function delArr() public {
        delete A;//对于动态数组就是数组置为0长度，对于静态数组就是将每一个元素置为0
    }

		function createMemory() public {
        //memrory数组只能使用定长形式
        //temp.pop() push() 无法在memory数组中使用，只可以读取或更新
        uint[] memory temp = new uint[](10);
       
    }
}
```

15. struct

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract StructTest {
    struct People {
        string name;
        uint8 age;
    }

    People[] list;

    function getIndex(uint index) public view returns (People memory) {
        return list[index];
    }

    function init() public {
        People memory p1 = People("aaa", 20);
        People memory p2 = People({name : "bbb", age : 30});
        People memory p3;
        p3.name = "ccc";
        p3.age = 12;

        list.push(p1);
        list.push(p2);
        list.push(p3);
    }

    function del() public {
        People storage p1 = list[0];
        p1.name = "sdsad";
        delete p1.age;
    }
}
```

16. memory storegae calldata

- 值传递

变量为值类型时，赋值给其他变量，是复制一个新的值过去，对新变量的操作不会影响原来变量

- 引用传递

传递的是变量的指针

如果变量类型是值类型：

布尔类型（bool）、整型（int）、地址类型（address）、定长字节数组（bytes）、枚举类型（enums）、函数类型（function）

那么传递一定是值传递，也意味着这些变量不能用memory，storage修饰。

如果变量类型是：字符串（string）、数组（array）、结构体（structs）、字典（mapping）、不定长字节数组（bytes）

那么决定于该变量是Storage类型还是Memory类型。

Storage 是把变量永久储存在区块链中，Memory 则是把变量临时放在内存中，类似电脑中的硬盘和内存，在函数外部声明的变量默认都是storage类型。

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Memory {
    string a = "hello";
    
    uint i = 20;

    function getStorage() public returns (string memory) {
        //由于局部引用类型变量不知道是值传递还是引用传递，必须声明为memory或storage
        string storage temp = a;
        a = "the mod......";
        //由于storage修饰，temp和a指向同一个数据，修改a指向的数据，temp返回也会修改
        //output：the mod......
        return temp;
    }

    function getMemory() public returns (string memory) {
        string memory temp = a;
        a = "the mod......";
        //由于memory修饰，temp相当于拷贝一份，修改a指向的数据不会影响temp
        //output：hello
        return temp;
    }

    //function的参数和返回值中如果有引用类型变量，必须声明为memory或calldata
    //calldata表示变量只读，不可修改
    function getCallData(uint[] calldata temp) public {
        //如果temp变量是memory，那么调用_modi函数时，会先复制一份数据然后传递
        //如果时calldata，此变量不可修改，那么会直接传递数据本身，减少一次复制操作，可能会降低gas费
        _modi(temp);
    }

    function _modi(uint[] calldata temp) internal {
        i = temp[0];
    }
}
```

17. 继承

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract People {
    function getPeopleName() public pure virtual returns (string memory) {
        return "people";
    }
}
contract Parent is People {
    uint private num = 100;

    uint other = 200;

    //如果需要被子类继承，必须用virtual修饰
    function getA() public pure virtual returns (string memory) {
        return "AParent";
    }

    function getB() public pure virtual returns (string memory) {
        return "BParent";
    }
}

contract Other {
    function getB() public pure virtual returns (string memory) {
        return "BOther";
    }

}


//多重继承时注意顺序要先写父类，再写派生类，其他不存在继承关系可以随意放
//contract Son is Parent, People 无法通过
//contract Son is People, Other, Parent 可以通过
contract Son is People, Parent, Other {
    function getOther() public view returns (uint) {
        //父类私有变量不可访问，公有变量可以访问
        //return num;
        return other;
    }

    //如果覆写父类方法，必须用override修饰
    function getA() public pure override returns (string memory) {
        return "ASon";
    }

    //如果父类中方法冲突，必须覆写，并指定所有有该方法的父类
    function getB() public pure override(Other, Parent) returns (string memory) {
        return "BSon";
    }

    //调用父类方法，形式1，明确之前父类
    function getSuper1() public pure returns (string memory) {
        return Parent.getB();
    }

    //形式2 使用super调用，注意如果多个父类有一个方法，与基础顺序有关，调用的是最后的继承的父类的方法
    function getSuper2() public pure returns (string memory) {
        return super.getB();
    }
}

//父类构造器

contract S {
    string name;

    constructor (string memory _name) {
        name = _name;
    }
}

contract T {
    uint age;

    constructor (uint _age) {
        age = _age;
    }
}

//形式1：contract own is S("aaa"), T(20)注入父类构造器
//形式3：形式1和形式2混合，可以一个从is中注入，一个从构造器中注入
contract own is S, T {

    uint public count;
    //形式2：在派生类构造器中指定
    constructor (string memory _name, uint _age, uint _count) S(_name) T(_age) {
        count = _count;
    }

    function getName() public view returns (string memory) {
        return name;
    }

    function getAge() public view returns (uint ) {
        return age;
    }
}
```

18. abi相关

```solidity
abi.encode(…) returns (bytes)：计算参数的ABI编码
abi.decode(bytes memory encodedData, (...)) returns (...): abi解码
	abi.decode(data, (uint, uint[2], bytes)) = (uint a, uint[2] memory b, bytes memory c) =

abi.encodePacked(…) returns (bytes)：与encode类似，计算参数的紧密打包编码，会压缩一些信息，减少空间占用，但是会可能出现潜在hash冲突

// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract AbiTest {
    uint x;

    function getX() public view returns (uint) {
        return x;
    }

    function setX(uint _x) public returns (uint) {
        x = _x;
    }

    function getEncode() public pure returns (bytes memory) {
        uint[] memory temp = new uint[](10);
        return abi.encode(1, "sda", 6, temp);
    }

    function getEncodePacked() public pure returns (bytes memory) {
        uint[] memory temp = new uint[](10);
        uint8 i1 = 1;
        uint8 i2 = 20;
        return abi.encodePacked(i1, "sda", i2, temp);
    }

    function getDecode() public pure returns (uint8, string memory, uint8) {
        uint i1 = 1;
        string memory i2 = "asds";
        uint i3 = 6;
        bytes memory data = abi.encode(i1, i2, i3);

        return abi.decode(data, (uint8, string, uint8));
    }

    function getEncodeWithSignature() public pure returns (bytes memory) {
        //output:0x0d1a4c480000000000000000000000000000000000000000000000000000000000000014
        //其中0d1a4c48就是函数签名
        return abi.encodeWithSignature("setX(uint)", 20);
    }

    function getEncodeWithSelector() public pure returns (bytes memory) {
        //和 abi.encodeWithSignature("setX(uint)", 20);等效
        //byte1是一个字节int8 byte4是4个字节 int32
        //对某方法hash256之后取前四个字节
        return abi.encodeWithSelector(bytes4(keccak256(bytes("setX(uint)"))), 20);
    }

    function getEncodeCall() public pure returns (bytes memory) {
        //todo
        return bytes("s");
    }
}
```