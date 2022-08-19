```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Destory {
    constructor() payable {}

		//自毁并转出所有余额，自毁后所有方法失效
		//注意如果合约有reciive fallback方法，仍然可以接受转账但相当于转入黑洞
    function kill() external {
        selfdestruct(payable(msg.sender));
    }

    function getBalance() external view returns (uint) {
        return address(this).balance;
    }
    
    function test() external pure returns (uint) {
        return 123;
    }
}
```