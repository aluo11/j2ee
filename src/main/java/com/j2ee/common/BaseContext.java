package com.j2ee.common;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取当前登录用户的id
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
