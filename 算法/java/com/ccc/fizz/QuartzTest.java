package com.ccc.fizz;

import com.ccc.fizz.base.utils.QuartzUtils;
import com.ccc.job.TestJob;

public class QuartzTest {
    public static void main(String[] args) {
        QuartzUtils.SimpleJob("job","trigger", TestJob.class, 3, 1);

    }
}
