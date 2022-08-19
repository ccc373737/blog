```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Destory {

    event Deposit(uint amount);

    event WithDraw(uint amount);

    address public owner = msg.sender;

    receive() external payable {
        emit Deposit(msg.value);
    }

    function withDraw() external {
        require(msg.sender == owner, "not owner");
        
        emit WithDraw(address(this).balance);

        //payable(owner).transfer(address(this).balance);
        selfdestruct(payable(owner));
    }
}
```