
ERC20相当于在以太链上发币，本质上是一个dapp，所以此种代币交易一般修改balancemap中数量即可。

借助以太坊的存储能力存储代币相关信息（不可修改），当然所有存储信息仍然需要ETH GAS费。

ERC20是以太坊的一种标准（智能合约模板），所有符合这个标准的数字货币都可以成为ERC20代币，自然也就能存入支持以太币的钱包中

拥有专属区块链的加密资产从技术上来说是一种币，而在第三方区块链上创建的所有其他资产则称为代币。

```solidity
// SPDX-License-Identifier: GPL-3.0

import "@openzeppelin/contracts/token/ERC20/IERC20.sol";

pragma solidity >=0.7.0 <0.9.0;

contract MyERC20 is IERC20 {

    uint public _totalNumber;

    mapping(address => uint) public _balanceMap;

    mapping(address => mapping(address => uint)) _allowanceMap;

    constructor(uint amount) {
        _mint(msg.sender, amount);
    }
 
     /**
      * @dev Returns the amount of tokens in existence.
      */
    function totalSupply() external view virtual override returns (uint256) {
        return _totalNumber;
    }
 
     /**
      * @dev Returns the amount of tokens owned by `account`.
      */
    function balanceOf(address account) external view virtual override returns (uint256) {
        return _balanceMap[account];
    }
 
     /**
      * @dev Moves `amount` tokens from the caller's account to `to`.
      *
      * Returns a boolean value indicating whether the operation succeeded.
      *
      * Emits a {Transfer} event.
      */
    function transfer(address to, uint256 amount) external virtual override returns (bool) {
        _transfer(msg.sender, to, amount);
        return true;
    }
 
     /**
      * @dev Returns the remaining number of tokens that `spender` will be
      * allowed to spend on behalf of `owner` through {transferFrom}. This is
      * zero by default.
      *
      * This value changes when {approve} or {transferFrom} are called.
      */
    function allowance(address owner, address spender) external view virtual override returns (uint256) {
        return _allowanceMap[owner][spender];
    }
 
     /**
      * @dev Sets `amount` as the allowance of `spender` over the caller's tokens.
      *
      * Returns a boolean value indicating whether the operation succeeded.
      *
      * IMPORTANT: Beware that changing an allowance with this method brings the risk
      * that someone may use both the old and the new allowance by unfortunate
      * transaction ordering. One possible solution to mitigate this race
      * condition is to first reduce the spender's allowance to 0 and set the
      * desired value afterwards:
      * https://github.com/ethereum/EIPs/issues/20#issuecomment-263524729
      *
      * Emits an {Approval} event.
      */
    function approve(address spender, uint256 amount) external virtual override returns (bool) {
        _approve(msg.sender, spender, amount);
        return true;
    }
 
     /**
      * @dev Moves `amount` tokens from `from` to `to` using the
      * allowance mechanism. `amount` is then deducted from the caller's
      * allowance.
      *
      * Returns a boolean value indicating whether the operation succeeded.
      *
      * Emits a {Transfer} event.
      */
    function transferFrom(
         address from,
         address to,
         uint256 amount
    ) external virtual override returns (bool) {
        require(_allowanceMap[from][msg.sender] >= amount, "insufficient allowance");
        require(_balanceMap[from] >= amount, "insufficient balance");//检查，后修改，减少一部分回滚操作

        _approve(from, msg.sender, _allowanceMap[from][msg.sender] - amount);
        _transfer(from, to, amount);
        return true;
    }

    function _transfer(address from, address to, uint256 amount) internal virtual {
        require(from != address(0), "Not allow from zero address");
        require(to != address(0), "Not allow to zero address");

        require(_balanceMap[from] >= amount, "insufficient balance");

        _balanceMap[from] -= amount;//0.8版本有下溢检查，本身余额不够也会抛出异常
        _balanceMap[to] += amount;
        emit Transfer(from, to, amount);
    }

    function _approve(address from, address to, uint256 amount) internal virtual {
        require(from != address(0), "Not allow from zero address");
        require(to != address(0), "Not allow to zero address");

        _allowanceMap[from][to] = amount;
        emit Approval(from, to, amount);
    }

    function _mint(address owner, uint amount) internal virtual {
        _totalNumber = amount;
        _balanceMap[owner] = amount;
    }

    function burn(uint amount) external virtual {
        require(_balanceMap[msg.sender] >= amount, "insufficient balance");

        _totalNumber -= amount;
        _balanceMap[msg.sender] -= amount;
    }
}
```