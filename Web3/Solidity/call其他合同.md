```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract TestCall {
    uint public x;
    string public y;

    function setX(uint _x) external {
        x = _x;
    }

    function getX() external view returns (uint) {
        return x;
    }

    function setXY(string memory _y) external payable {
        x = msg.value;
        y = _y;
    }

    function getXY() external view returns (uint, string memory) {
        return (x, y);
    }
}

contract Call {
    function setX(address _addr, uint _x) external payable {
        TestCall(_addr).setX(_x);
    }

    function getX(TestCall _test) external view returns (uint) {
        return _test.getX();
    }

    function setXY(address _addr, string memory _y) external payable {
        TestCall(_addr).setXY{value: msg.value}(_y);
    }

    function getXY(address _addr) external view returns (uint x, string memory y) {
        (x, y) = TestCall(_addr).getXY();
    }
}
```

调用其他合同方法：

1. import或复制其他合约到文件中
2. 使用继承调用父类方法
3. 使用interface

```python
interface TestCall {
    function setX(uint _x) external;
}

contract Call {
    function setX(address _addr, uint _x) external payable {
        TestCall(_addr).setX(_x);
    }
}
```