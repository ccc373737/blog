```solidity

// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract TimeLock {
    address public owner;

    uint public constant MIN_DELAY = 10; // seconds
    uint public constant MAX_DELAY = 1000; // seconds
    uint public constant EXPIRE = 1000;

    mapping(bytes32 => bool) public txMap;

    event SubmitEvent(address _addr, uint _value, string _funStr, bytes _data, uint _timestamp);
    event ExecuteEvent(address _addr, uint _value, string _funStr, bytes _data, uint _timestamp);

    constructor() payable {
        owner = msg.sender;
    }

    modifier onlyOwner() {
        require(msg.sender == owner, "not owner");
        _;
    }

    function getTxId(address _addr, uint _value, string calldata _funStr, bytes calldata _data, uint _timestamp) 
        public 
        pure
        returns (bytes32) {

        return keccak256(abi.encodePacked(_addr, _value, _funStr, _data, _timestamp));
    }

    function submit(address _addr, uint _value, string calldata _funStr, bytes calldata _data, uint _timestamp)
        external
        onlyOwner {

        require(_timestamp > block.timestamp + MIN_DELAY && _timestamp < block.timestamp + MAX_DELAY, "need between MIN_DELAY and MAX_DELAY");

        bytes32 txId = getTxId(_addr, _value, _funStr, _data, _timestamp);

        require(!txMap[txId], "already transcation");

        txMap[txId] = true;

        emit SubmitEvent(_addr, _value, _funStr, _data, _timestamp);
    }

    function execute(address _addr, uint _value, string calldata _funStr, bytes calldata _data, uint _timestamp) 
        external
        onlyOwner {
        
        bytes32 txId = getTxId(_addr, _value, _funStr, _data, _timestamp);

        require(txMap[txId], "transcation have not submited");
        require(block.timestamp > _timestamp, "Time has not arrived yet");
        require(block.timestamp < _timestamp + EXPIRE, "Time has expired");

        txMap[txId] = false;

        bytes memory data;
        if (bytes(_funStr).length > 0) {
            data = abi.encodeWithSignature(_funStr, _data);
            //data = abi.encodePacked(bytes4(keccak256(bytes(_funStr))), _data);
        } else {
            data = _data;
        }

        (bool success,) = _addr.call{value: _value}(data);

        require(success, "tx failed");

        emit ExecuteEvent(_addr, _value, _funStr, _data, _timestamp);
    }
}

contract Called {
    event ReceiveEvent(address _addr, uint _value, string _message);

    receive() external payable{}

    function timeHelper() public view returns (uint) {
        return block.timestamp;
    }

    function argl(string memory _message) public payable returns (string memory) {
        emit ReceiveEvent(msg.sender, msg.value, _message);
        return "mysdsacc";
    }

    function getBalance() public view returns(uint) {
        return address(this).balance;
    }
}
```