```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

import "./_myerc20.sol";

contract Vault {
    MyERC20 public immutable token;

    uint public totalSupply;

    mapping (address => uint) public balanceOf;

    constructor(address addr) {
        token = MyERC20(addr);
    }

    function _mint(address addr, uint shares) private {
        totalSupply += shares;
        balanceOf[addr] += shares;
    }

    function _burn(address addr, uint shares) private {
        totalSupply -= shares;
        balanceOf[addr] -= shares;
    }

    function deposit(uint amount) external {
        /*
        a = amount
        B = balance of token before deposit
        T = total supply
        s = shares to mint

        s = a * T / B
        */
        uint shares;
        if (totalSupply == 0) {
            shares = amount;
        } else {
            shares = amount * totalSupply / token.balanceOf(address(this));
        }

        _mint(msg.sender, shares);
        token.transferFrom(msg.sender, address(this), amount);
    }

    function withdraw(uint shares) external {
        //a = sB / T
        //通常用户锁仓期间会有其他方式进入金额，即token.balanceOf(address(this)的值变大，增加了收益系数作为用户的奖励
        uint amount = (shares * token.balanceOf(address(this))) / totalSupply;
        _burn(msg.sender, shares);
        token.transfer(msg.sender, amount);
    }
}
```