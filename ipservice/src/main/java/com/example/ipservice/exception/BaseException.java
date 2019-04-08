package com.example.ipservice.exception;

/**
 * Created by jacob on 2019/4/8.
 */
public class BaseException extends RuntimeException {

    private int code;

    public BaseException(String message) {
        super(message);
        this.code = 9999;
    }

    public BaseException(Integer code, String message) {
        super(message);
        assert code != null;
        this.code = code;
    }

}
