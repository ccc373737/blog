package com.ccc.fizz.exce;

public class ExeDemo {

    public void getMsg() throws DefineException {
        int a = 1 / 0;
        if (3 == 4) {

        } else {
            throw new DefineException("自定义异常。。。", null);
        }
    }
}
