```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract Selector {

    event Log(bytes data);

    function getSelector(string calldata _func) external pure returns(bytes4) {
        //"test(address,uint256)" => 0xba14d606
        //必须指定完整参数。如uint必须指定uint256
        return bytes4(keccak256(bytes(_func)));
    }

    function test(address _addr, uint _amount) external {
        //data: 
        //函数名：0xba14d606
        //第一个参数：00000000000000000000000017f6ad8ef982297579c203069c1dbffe4348c372
        //第二个参数：000000000000000000000000000000000000000000000000000000000000000e
        emit Log(msg.data);
    }
}
```