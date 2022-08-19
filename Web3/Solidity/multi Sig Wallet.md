```solidity
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract MultiSigWallet {

    struct Transaction {
        address to;
        uint value;
        bytes data;
        bool executed;
        uint approveCount; 
    }

    uint public immutable approveRate;

    mapping (address => bool) public isOwnerMap;

    mapping (uint => mapping (address => bool)) public approvedMap;

    address[] public owners;

    Transaction[] public transactions;

    modifier onlyOwner() {
        require(isOwnerMap[msg.sender], "not owner");
        _;
    }

    modifier needExisted(uint index) {
        require(index < transactions.length, "transaction not exist");
        _;
    }

    modifier needNonExecution (uint index) {
        require(!transactions[index].executed, "transaction is executed");
        _;
    }

    event Deposit(address indexed add, uint value, uint balance);
    event AddOwnerEvent(address indexed add, address approvedPerson);
    event SubmitEvent(uint index, address to, uint value, bytes data);
    event ApproveEvent(uint index, address addr);
    event RevokeEvent(uint index, address addr);
    event ExecuteEvent(uint index, address to, uint value, bytes data);

    constructor(address[] memory _addrs, uint _approveRate) {
        require(_addrs.length > 0, "need owners > 0");
        require(_approveRate >= 0 && _approveRate <= 100, "approveRate need 0-100");

        for (uint i = 0; i < _addrs.length; i++) {
            if (_addrs[i] == address(0)) {
                revert("Zero Address!");
            }

            if (isOwner(_addrs[i])) {
                revert("already exist Address!");
            }

            owners.push(_addrs[i]);
            isOwnerMap[_addrs[i]] = true;
        }
        approveRate = _approveRate;
    }

    receive() external payable {
        emit Deposit(msg.sender, msg.value, address(this).balance);
    }

    function getBalance() public view returns (uint) {
        return address(this).balance;
    }

    function addOwner(address _addr) external onlyOwner {
        require(_addr != address(0), "Zero Address!");
        require(!isOwner(_addr), "already exist Address!");

        owners.push(_addr);
        isOwnerMap[_addr] = true;
        emit AddOwnerEvent(_addr, msg.sender);
    }

    //remix上_data如果不是16进制数据，将会报错，测试：0x
    function submitTransaction(address _to, uint _value, bytes memory _data) external onlyOwner {
        uint index = transactions.length;

        transactions.push(Transaction(_to, _value, _data, false, 0));

        emit SubmitEvent(index, _to, _value, _data);
    }

    function approveTransaction(uint transactionIndex) 
        external 
        onlyOwner 
        needExisted(transactionIndex)
        needNonExecution(transactionIndex) {
        
        require(!approvedMap[transactionIndex][msg.sender], "you are already approved");

        approvedMap[transactionIndex][msg.sender] = true;
        transactions[transactionIndex].approveCount++;
        emit ApproveEvent(transactionIndex, msg.sender);
    }

    function revokeTransaction(uint transactionIndex) 
        external 
        onlyOwner 
        needExisted(transactionIndex)
        needNonExecution(transactionIndex) {

        require(approvedMap[transactionIndex][msg.sender], "you are not approved");
        approvedMap[transactionIndex][msg.sender] = false;
        transactions[transactionIndex].approveCount--;
        emit RevokeEvent(transactionIndex, msg.sender);
    }

    function executeTransaction(uint transactionIndex) 
        external 
        needExisted(transactionIndex)
        needNonExecution(transactionIndex) {

        Transaction storage tran = transactions[transactionIndex];

        require(tran.approveCount >= owners.length * approveRate / 100, "cannot execute tx");

        (bool success,) = payable(tran.to).call{value: tran.value}(tran.data);

        require(success, "pay failed");

        tran.executed = true;

        emit ExecuteEvent(transactionIndex, tran.to, tran.value, tran.data);
    }

    function isOwner(address addr) public view returns (bool) {
        return isOwnerMap[addr]; 
    }
}
```