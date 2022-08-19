```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract WETH {
    string public name     = "Wrapped Ether";
    string public symbol   = "WETH";
    uint8  public decimals = 18;

    event Deposit(address indexed addr, uint indexed amount);

    event Withdraw(address indexed addr, uint indexed amount);

    mapping (address => uint) public  balanceOf;

    receive() external payable {
        deposit();
    }

    function deposit() public payable {
        balanceOf[msg.sender] += msg.value;
        emit Deposit(msg.sender, msg.value);
    }

    function withdraw(uint wad) external {
        require(balanceOf[msg.sender] >= wad);
        balanceOf[msg.sender] -= wad;
        payable(msg.sender).transfer(wad);
        emit Withdraw(msg.sender, wad);
    }
}
```