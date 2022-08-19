```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

interface IReal {
    function setNumber(uint number) external;
    function getNumber() external view returns(uint);
}

contract Real1 {
    uint number;

    function setNumber(uint _number) external {
        number = _number;
    }

    function getNumber() external view returns(uint) {
        return number;
    }
}

contract Real2 {
    uint number;

    function setNumber(uint _number) external {
        number = _number + 32;
    }

    function getNumber() external view returns(uint) {
        return number;
    }
}

contract Proxy {
    IReal public real;

    function setReal(address _addr) external {
        real = IReal(_addr);
    }

    function setNumber(uint _number) external {
        real.setNumber(_number);
    }

    function getNumber() external view returns(uint) {
        return real.getNumber();
    }
}
```