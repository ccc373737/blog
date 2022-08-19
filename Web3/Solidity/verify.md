签名算法是`secp256k1`
，哈希算法选择了`keccak256`

```python
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

//1.计算message值 Keccak256("\x19Ethereum Signed Message:\n32" + Keccak256(message))
//2.根据签名_sig计算 v r s
//3.ecrecover函数恢复地址ecrecover(messageHash, v, r, s);

contract VerifySig {
    function verify(address _signer, string memory _message, bytes memory _sig) external pure returns (bool) {

        bytes32 messageHash = getMessageHash(_message);

        bytes32 ethSignedMessageHash = getSignedMessageHash(messageHash);

        return recover(ethSignedMessageHash, _sig) == _signer;
    }

    function getMessageHash(string memory _message) public pure returns (bytes32) {
        return keccak256(abi.encodePacked(_message));
    }

    function getSignedMessageHash(bytes32 _messageHash) public pure returns (bytes32) {
        return keccak256(abi.encodePacked("\x19Ethereum Signed Message:\n32",_messageHash));
    }

    function recover(bytes32 _ethSignedMessageHash, bytes memory _sig) public pure returns (address) {
        (bytes32 r, bytes32 s, uint8 v) = _split(_sig);
        return ecrecover(_ethSignedMessageHash, v, r, s);
    }

    function _split(bytes memory _sig) internal pure returns (bytes32 r, bytes32 s, uint8 v) {
        require(_sig.length == 65, "invalid sig");

        assembly {
            r := mload(add(_sig, 32))//前32位是数据长度，从32位开始取值
            s := mload(add(_sig, 64))//第二个32位
            v := byte(0, mload(add(_sig, 96)))//96位后一个字节
        }
    }

}

assembly：内联汇编，已汇编语言形式嵌入到Solidity源码中，基于栈的形式
Fault attack
```