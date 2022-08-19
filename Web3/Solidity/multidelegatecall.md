```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.13;

contract CallTest {
    error DelegatecallFailed();

    function multiDelegatecall(bytes[] memory data)
        external
        payable
        returns (bytes[] memory results)
    {
        results = new bytes[](data.length);

        for (uint i; i < data.length; i++) {
            (bool ok, bytes memory res) = address(this).delegatecall(data[i]);
            if (!ok) {
                revert DelegatecallFailed();
            }
            results[i] = res;
        }
    }
}

contract DelegateMutiCall {

    event CallEvent(address addr, string name);

    mapping (address => uint) balanceMap;

    function test1(uint x, uint y) external {
        emit CallEvent(msg.sender, "test1");
    }
    //方法重载？
    function test1(uint x, string memory str) external {
        emit CallEvent(msg.sender, "test2");
    }

    function mint() external payable {
        balanceMap[msg.sender] = msg.value;
        emit CallEvent(msg.sender, "mint");
    }
}

contract Util {
    function getTest1Code(uint x, uint y) external pure returns(bytes memory data) {
        return abi.encodeWithSelector(DelegateMutiCall.test1.selector, x, y);
    }

    function getTest2Code(uint x, string memory str) external pure returns(bytes memory data) {
        return abi.encodeWithSelector(DelegateMutiCall.test2.selector, x, y);
    }

    function getMintCode() external pure returns(bytes memory data) {
        return abi.encodeWithSelector(DelegateMutiCall.mint.selector);
    }
}
```