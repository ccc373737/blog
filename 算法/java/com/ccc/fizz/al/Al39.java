package com.ccc.fizz.al;

public class Al39 {
    public boolean isStraight(int[] nums) {
        int index = 0;
        int zeroCount = 0;

        boolean isStart = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroCount++;
            } else {
                if (isStart) {
                    index++;
                    while (index < nums[i]) {
                        if (zeroCount <= 0) {
                            return false;
                        } else {
                            zeroCount--;
                            index++;
                        }
                    }
                } else {
                    index = nums[i];
                    isStart = true;
                }
            }
        }
        return true;
    }
}
