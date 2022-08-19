```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.6;

contract Test {
    function test1() external view returns (uint, uint) {
        return (7, block.timestamp);
    }

    function test2() external view returns (uint, uint) {
        return (9, block.timestamp);
    }

    function getTest1Code() external pure returns (bytes memory) {
        //abi.encodeWithSignature("test1()")
        return abi.encodeWithSelector(this.test1.selector);
    }

    function getTest2Code() external pure returns (bytes memory) {
        return abi.encodeWithSelector(this.test2.selector);
    }
}

contract CallTest {
    function multiCall(address[] calldata targets, bytes[] calldata data) external view returns(bytes[] memory) {

        bytes[] memory results = new bytes[](targets.length);

        for (uint i = 0; i < targets.length; i++) {
            (bool success, bytes memory result) = targets[i].staticcall(data[i]);
            require(success, "call failed");

            results[i] = result;
        }

        //0x000000000000000000000000000000000000000000000000000000000000000700000000000000000000000000000000000000000000000000000000628cdb83,
        //0x000000000000000000000000000000000000000000000000000000000000000900000000000000000000000000000000000000000000000000000000628cdb83
        return results;
    }
}
```