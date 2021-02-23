package com.ccc.fizz.exce;

public class DefineException extends Exception {
    public String message;
    public Exception e;

    public DefineException(String message, Exception e) {
        this.message = message;
        this.e = e;
    }

    public String getMessage() {
        return message;
    }

    public Exception getE() {
        return e;
    }
}
