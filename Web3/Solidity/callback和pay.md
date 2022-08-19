```
触发fallback() 还是 receive()?
           接收ETH
              |
         msg.data是空？
            /  \
          是    否
          /      \
receive()存在?   fallback()
        / \
       是  否
      /     \
receive()   fallback()
```

## about pay

```solidity
//如果异常会转账失败，抛出异常(等价于requi(send()))（合约地址转账）
// 有gas限制，最大2300
//reverts
<address payable>.transfer(uint256 amount)
 
//如果异常会转账失败，仅会返回false，不会终止执行（合约地址转账）
// 有gas限制，最大2300
//return bool
<address payable>.send(uint256 amount) returns (bool)
 
如果异常会转账失败，仅会返回false，不会终止执行（调用合约的方法并转账）
// 没有gas限制
//return bool and data
<address>.call(bytes memory) returns (bool, bytes memory)
(bool success, bytes memory data) = _addr.call{value : msg.value, gas: gas}("sss");
```

注意上述的gas是指回调函数fallback中消耗的资源，即如果接受的地址是一个合约地址，那么只有2300gas来执行回调函数的一些代码。

如果转账地址是个人，则不存在此gas，转账地址是合约才有可能消耗节点资源

transfer和send方法2300gas的限制基本上就是回调函数中只执行一个event事件所消耗的资源。

而call方法无限制，更灵活，但可能会有安全问题。

//(””)就是无参数的意思，将调用receive回调

 _to.call{value:msg.value, gas:500000}("");

//("sss")表示有参数，如果参数无意义（或找不到对应方法），将调用fallback

//如果有参数，对方没有fallback方法，将失败

 _to.call{value:msg.value, gas:500000}("sss");

//(abi.encodeWithSignature("argl(string)", "call argl")有意义的参数将调用接受合约特定方法

```solidity
function argl(string memory _message) public payable returns (string memory) {
        emit Received(msg.sender, msg.value, _message);
        return "mysdsacc";
    }

//如果有特定payable方法，fallback将不会被调用，也可以不存在
```

_addr.call{value : msg.value}(abi.encodeWithSignature("argl(string)", "call argl"));

基本上call方法可以调取对方合约任意payable方法。

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Pay {

    event Log(string func, address caller, uint amount, bytes message, uint gas);

    receive() external payable {
        //"gas": "8875"
        //transfer和send有2300gas的限制，无法调用本日志。gasleft():remaining gas
        //transfer和send只能执行一些有限的功能
        emit Log("receive", msg.sender, msg.value, "", gasleft());
    }

    function getBalance() external view returns (uint) {
        return address(this).balance;
    }
}

contract Send {

    constructor() payable {}//表示构造函数时可以接受msg.value金额

    receive() external payable {}

    function send(address payable _to) external payable {
        bool isSend = _to.send(msg.value);
        require(isSend, "send fail");
    }

    function transfer(address payable _to) external payable {
        //_to.transfer(msg.value); msg.value是从转出caller的金额，345是转出当前合约钱包的金额
        _to.transfer(msg.value);
    }

    function calls(address payable _to) external payable {
         (bool isSend,) = _to.call{value:msg.value, gas:500000}("");
         require(isSend, "call Fail");
    }

     function getBalance() external view returns (uint) {
        return address(this).balance;
    }
}
```

```solidity
//send和transfer收到2300gas费的限制
//建议只使用call方法
function send(address payable _to) external payable {
        //参数中需要用payable修饰，_to是接受者，msg是调用方的转账金额
        bool isSend = _to.send(msg.value);
        require(isSend, "send fail");
    }

    function transfer(address payable _to) external payable {
         _to.transfer(msg.value);
    }

    function calls(address payable _to) external payable {
         (bool isSend,) = _to.call{value:msg.value, gas:500000}("");
         require(isSend, "call Fail");
    }
```

```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Receive {

    event Received(address caller, uint amount, string message);

    //一个合约只能有一个receive函数，该函数不能有参数和返回值，需设置为external，payable
    //当接受无参转账时，触发该函数，如果没有此函数，则合约账户无法接受转账
    receive() external payable {
        emit Received(msg.sender, msg.value, "Reeeeeeeceived");
    }

    //一个合约只能有一个receive函数，该函数不能有参数和返回值，需设置为external；可设置为payable
    //可接受带参数的转账，如果没有该函数，有参转账将出错
    fallback() external payable {
        emit Received(msg.sender, msg.value, "falllllback");
    }

    //_addr.call{value : msg.value}(abi.encodeWithSignature("argl(string)", "call argl"));
    //使用此种形式传参，可以调用到特定的方法
    //有参数，但没有对应的方法，将调取fallback函数
    function argl(string memory _message) public payable returns (string memory) {
        emit Received(msg.sender, msg.value, _message);
        return "mysdsacc";
    }

    function getAddress() public view returns(address) {
        return address(this);
    }

    function getBalance() public view returns(uint) {
        return address(this).balance;
    }
}

contract Call {
    Receive receiver;

    constructor() {
        receiver = new Receive();
    }

    event Response(bool success, bytes data);

    function testCall(address payable _addr) public payable {
        //(bool success, bytes memory data) = _addr.call{value : msg.value}("sss");

        (bool success, bytes memory data) = _addr.call{value : msg.value}(abi.encodeWithSignature("argl(string)", "call argl"));
        emit Response(success, data);
    }

    function getAddress() public view returns(address) {
        return receiver.getAddress();
    }

    function getBalance() public view returns(uint) {
        return receiver.getBalance();
    }
}

contract FallBack {
    event Log(string func, address caller, uint amount, bytes message);

    //通过Low level interactions直接转账，带参数或不带参数，智能合约本身无法拒绝转账
    fallback() external payable {
        emit Log("fallback", msg.sender, msg.value, msg.data);
    }

    receive() external payable {
         emit Log("receive", msg.sender, msg.value, "");
    }

    function getBalance() external view returns (uint) {
        return address(this).balance;
    }

}
```

一个加上payable就表示可以接受转账，如果参数中有msg.value将自动转给当前合约账户，当然需要现有receive和fallback函数

```solidity
		function save() public payable {
				//此行代码就是payable本身效果，将value发给自己，此行可加可不加
        payable(address(this)).transfer(msg.value);
    }

		function getBalance() public view returns (uint) {
        return address(this).balance;
    }

    receive() external payable {
    }

    fallback() external payable {
    }
```