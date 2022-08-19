```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

library Math {
    function max(uint x, uint y) public pure returns (uint) {
        return x >= y ? x : y;
    }
}

library Array {
    function getIndex(uint[] calldata arr, uint value) public pure returns (uint) {
        for (uint i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }

        revert("not found");
    }
}

contract Test {
    //为uint[]数组赋予库的特性，getIndex中需要uint[]为第一个参数
    using Array for uint[];

    uint[] arr =  [1,2,3,4,5];

    function testMax(uint x, uint y) external pure returns (uint) {
        return Math.max(x, y);
    }

    function getIndex(uint value) external view returns (uint) {
        //return Array.getIndex(arr, value);
        return arr.getIndex(value);
    }
}
```