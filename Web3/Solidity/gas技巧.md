1.  删除不必要的库Solidity库，所有导入的库代码最后都需要上链保存
2. 合理选择变量类型，uint160等
3. 将多个小变量放入同一个slot中，一个slot 256大小 [存储结构](https://www.notion.so/9283162a688e4424af085a98fe636582) 
4. 合理使用优化器optimizer
5. 使用内联汇编将多个变量打包到单个slot中
6. 使用本地变量，使用calldata等

```python
// SPDX-License-Identifier: MIT
pragma solidity ^0.8;

contract Gas {
    //start 52559
    //使用calldata 50254
    //使用短路与和自增
    //使用本地变量缓存，不用每次读public变量并修改 49824 
    //缓存长度和nums[i] 49493 
    uint public total;
    //[1,2,3,4,5,6,102,104]
    function sumEven(uint[] calldata nums) external {
        uint _total = total;
        uint len = nums.length;
        for (uint i = 0; i < len; i++) {
            uint temp = nums[i];
            if (temp % 2 == 0 && temp < 100) {
                _total += temp;
            }
        }

        total = _total;
    }
}
```