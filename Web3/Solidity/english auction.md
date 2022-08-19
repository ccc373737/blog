```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

import "./_ERC721.sol";
import "hardhat/console.sol";

contract Auction {

    uint public constant DURATION = 7 days;

    address public immutable seller;
    uint public immutable reservePrice;
    uint public immutable minimumAddPrice;

    ERC721 public immutable nft;
    uint public immutable nftId;

    bool public isStart;
    uint public endTime;

    mapping(address => uint) public bidMap;
    address highestBid;
    uint highestPrice;

    event Start(uint endTime);
    event Bid(address indexed addr, uint price);
    event Withdraw(address indexed addr, uint amount);
    event End(address indexed bider, uint bidPrice);

    constructor(uint _reservePrice, uint _minimumAddPrice, address _nftAddress, uint _nftId) {
        seller = msg.sender;
        reservePrice = _reservePrice;
        minimumAddPrice = _minimumAddPrice;

        nft = ERC721(_nftAddress);
        nftId = _nftId;
    }

    function start() external {
        require(!isStart,  "Already Start");

        isStart = true;
        endTime = block.timestamp + DURATION;
        //如果使用safeTransferFrom需要实现onERC721Received接口
        nft.transferFrom(seller, address(this), nftId);

        emit Start(endTime);
    }

    function getHighestPrice() public view returns (uint) {
        return highestPrice;
    }

    function bid() external payable {
        require(isStart,  "wait start...");
        require(block.timestamp <= endTime, "It's Over");
        //已出价格+加价
        uint highTemp = bidMap[msg.sender] + msg.value;
        //需要大于最低价和最低加价
        require(highTemp >= reservePrice, "less than reservePrice");
        require(highTemp >= highestPrice + minimumAddPrice, "less than minimumAddPrice");

        highestPrice = highTemp;
        highestBid = msg.sender;

        bidMap[msg.sender] = highTemp;

        emit Bid(msg.sender, msg.value);
    }

    function withdraw() external {
        require(bidMap[msg.sender] > 0, "No balance");
        //最高价不允许转出
        require(msg.sender != highestBid, "you are in biding");

        uint balance = bidMap[msg.sender];
        bidMap[msg.sender] = 0;

        payable(msg.sender).transfer(balance);

        emit Withdraw(msg.sender, balance);
    }

    function end() external {
        require(isStart,  "wait start...");
        require(block.timestamp > endTime, "can't end now");

        if (highestBid != address(0)) {
            nft.safeTransferFrom(address(this), highestBid, nftId);
            payable(seller).transfer(highestPrice);
        } else {
            nft.safeTransferFrom(address(this), seller, nftId);
        }

        emit End(highestBid, highestPrice);
    }
}
```