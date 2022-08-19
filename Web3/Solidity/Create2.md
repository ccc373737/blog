```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract TestContract {
    address public owner;

    constructor(address _owner) {
        owner = _owner;
    }
}

contract CreateFactory {
    event Deploy(address addr);

    function deploy(uint _salt) external {
        //相当于指定一个种子，作为生成地址算法的参数
        TestContract con = new TestContract{salt: bytes32(_salt)}(msg.sender);

        emit Deploy(address(con));
    }
    
    function getByteCode(address _owner) public pure returns(bytes memory) {
        bytes memory byteCode = type(TestContract).creationCode;

        return abi.encodePacked(byteCode, abi.encode(_owner));
    }

    function getAddress(bytes memory _byteCode, uint _salt) public view returns(address) {
        bytes32 hash = keccak256(abi.encodePacked(bytes1(0xff), address(this), _salt, keccak256(_byteCode)));

        return address(uint160(uint(hash)));
    }
}
```