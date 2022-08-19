```solidity
// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

import "./_ERC721.sol";

contract DutchAuction {

    uint public constant DURATION = 7 days;

    address public immutable seller;
    uint public immutable startPrice;
    uint public immutable discountRate;
    uint public immutable startTime;
    uint public immutable endTime;

    ERC721 public immutable nft;
    uint public immutable nftId;

    event Bid(address _addr, uint bidPirce);

    constructor(uint _startPrice, uint _discountRate, address _nftAddress, uint _nftId) {
        seller = msg.sender;
        startPrice = _startPrice;
        discountRate = _discountRate;
        startTime = block.timestamp;
        endTime = startTime + DURATION;

        nft = ERC721(_nftAddress);
        nftId = _nftId;
    }

    function getPrice() public view returns (uint) {
        uint elapsed = block.timestamp - startTime;
        uint discount = elapsed * discountRate;//

        return startPrice - discount;
    }

    function bid() external payable {
        require(block.timestamp <= endTime, "It's Over");

        uint currentPrice = getPrice();
        require(msg.value >= currentPrice, "less than token's current price");

        nft.transferFrom(seller, msg.sender, nftId);
        emit Bid(msg.sender, msg.value);

        uint refund = msg.value - currentPrice;
        if (refund > 0) {
            payable(msg.sender).transfer(refund);
        }

        selfdestruct(payable(seller));
    }
}
```