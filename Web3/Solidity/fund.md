```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

import "./_myerc20.sol";

contract CrowdFund {

    struct Project {
        address creator;
        uint goal;
        uint raisedNumber;
        uint startTime;
        uint endTime;
        bool claimed;
    }

    mapping (uint => mapping (address => uint)) public fundMap;

    Project[] public projects;

    MyERC20 public immutable token;

    constructor(address _tokenAddress) {
        token = MyERC20(_tokenAddress);
    }

    event LaunchEvent(address addr, uint goal, uint endTime);
    event FundEvent(address addr, uint index, uint amount);
    event RefundEvent(address addr, uint index, uint amount);
    event FundSuccessEvent(uint index);

    modifier needExisted(uint index) {
        require(index < projects.length, "project not exist");
        _;
    }

    modifier needNotFinish(uint index) {
        require(block.timestamp <= projects[index].endTime, "project is finished");
        _;
    }

    modifier needFinish(uint index) {
        require(block.timestamp > projects[index].endTime, "project is not finished");
        _;
    }

    function initiate(uint _goal, uint _durationDays) external {
        //todo days 
        uint endTime = block.timestamp + _durationDays;
        projects.push(Project(msg.sender, _goal, 0, block.timestamp, endTime, false));
        emit LaunchEvent(msg.sender, _goal, endTime);
    }

    function fund(uint index, uint amount) external 
        needExisted(index)
        needNotFinish(index) {

        token.transferFrom(msg.sender, address(this), amount);
        fundMap[index][msg.sender] += amount;
        projects[index].raisedNumber += amount;

        emit FundEvent(msg.sender, index, amount);
    }

    function refund(uint index, uint amount) external 
        needExisted(index)
        needNotFinish(index) {

        require(fundMap[index][msg.sender] >= amount, "insuffice");

        token.transfer(msg.sender, amount);

        fundMap[index][msg.sender] -= amount;
        projects[index].raisedNumber -= amount;

        emit RefundEvent(msg.sender, index, amount);
    }

    function claim(uint index) external 
        needExisted(index)
        needFinish(index) {
        
        Project storage pro = projects[index];

        require(pro.creator == msg.sender, "not creator");
        require(pro.raisedNumber >= pro.goal, "fund failed, not reach the goal");

        pro.claimed = true;
        token.transfer(pro.creator, pro.raisedNumber);

        emit FundSuccessEvent(index);
    }

    function reclaim(uint index) external 
        needExisted(index)
        needFinish(index) {
        
        require(projects[index].raisedNumber < projects[index].goal, "fund success, not allow reclaiming");
        require(fundMap[index][msg.sender] > 0, "fund balance is zero");

        fundMap[index][msg.sender] = 0;
        token.transfer(msg.sender, fundMap[index][msg.sender]);
    }
}
```