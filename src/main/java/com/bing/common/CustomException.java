package com.bing.common;

/**
 * 自定义业务异常
 * @author LiBingYan
 * @时间   2022/10/1
 */

public class CustomException extends RuntimeException{
    public CustomException(String msg) {
        super(msg);
    }

}
