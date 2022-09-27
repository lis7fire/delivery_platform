package com.bing.common;

/**
 * 基于 ThreadLocal 封装工具类，用户保存和获取当前登录用户id ，用于保存本线程内的部分变量。
 * 未使用，
 * @author: LiBingYan
 * @时间: 2022/9/27
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // 向 ThreadLocal 中写入变量，
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    // 从 ThreadLocal 提取变量值。
    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
