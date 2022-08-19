```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8;

contract SafeMath {

    function testFlow() public pure returns (uint) {
        uint a = 0;
        a--;//8.0会检查溢出数，相当于safemath功能，这里会报错
        return a;
    }

    function testFlowWithUncheck() public pure returns (uint) {
        uint a = 0;
        unchecked {a--;}
        return a;
    }
}
```

type指定类型：

type(int8).min;

type(uint16).max;

type(int256).min;