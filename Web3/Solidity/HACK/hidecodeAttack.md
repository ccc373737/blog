```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Test {
    Log public log;

    constructor(address addr) {
        log = Log(addr);
    }

    function test() external {
        log.record();
    }
}

contract Log {
    event LogEvent(address addr, string data);

    function record() external {
        emit LogEvent(msg.sender, "logger record");
    }
}

//这部分代码单独部署，方法和Log一致，在Test部署时注入
//solidity不会在注入时检查【类】，只要执行时有相同的方法就可以成功
contract Hide {
    event LogEvent(address addr, string data);

    function record() external {
        emit LogEvent(msg.sender, "Hide code .........");
    }
}
```