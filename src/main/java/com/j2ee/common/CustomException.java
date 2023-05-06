package com.j2ee.common;

/**
 * 自定义业务处理类
 */
public class CustomException extends Exception{
    public CustomException(String msg){
        super(msg);
    }
}
