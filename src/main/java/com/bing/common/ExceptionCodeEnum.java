/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.bing.common;

/**
 * 异常状态码列表
 *
 * @author: LiBingYan
 * @时间: 2022/9/26
 */

public enum ExceptionCodeEnum {
    //  所有枚举类都继承自： java.lang.Enum 类
    //1、定义枚举实例常量序列，枚举实例后的括号，表示调用私有构造方法；2、用逗号分隔开，
    ERROR(44444, "error"),

    SUCCESS(1, "success"),

    REQUEST_PARAMETER_NOT_MATCH(10400   , "用户请求 参数名 不匹配"),
    REQUEST_PARAMETER_VALUE_NOT_MATCH(10421   , "用户请求 参数值类型 不匹配"),
    SAME_LOGIN_NAME_EXIST(10111, "用户名已存在！"),
    LOGIN_NAME_NULL(10110, "请输入登录名！"),
    LOGIN_PASSWORD_NULL(10120, "请输入密码！"),
    LOGIN_VERIFY_CODE_NULL(10130, "请输入验证码！"),
    LOGIN_VERIFY_CODE_ERROR(10130, "验证码错误！"),

    LOGIN_ERROR(10200, "登录失败！"),

    LOGIN_USER_LOCKED(10202, "用户已被禁止登录！"),
    OPERATE_ERROR(20001, "系统执行出错，操作失败！"),

    NO_PERMISSION_ERROR(10301, "访问未授权无操作权限！"),

    DB_ERROR(30300, "数据服务出错，请检查输入后重试！");

    /**
     * DATA_NOT_EXIST("未查询到记录！"),
     * SAME_CATEGORY_EXIST("已存在同级同名的分类！"),
     * SAME_INDEX_CONFIG_EXIST("已存在相同的首页配置项！"),
     * GOODS_CATEGORY_ERROR("分类数据异常！"),
     * SAME_GOODS_EXIST("已存在相同的商品信息！"),
     * GOODS_NOT_EXIST("商品不存在！"),
     * GOODS_PUT_DOWN("商品已下架！"),
     * SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR("超出单个商品的最大购买数量！"),
     * SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR("超出购物车最大容量！"),
     * <p>
     * <p>
     * <p>
     * ORDER_NOT_EXIST_ERROR("订单不存在！"),
     * ORDER_ITEM_NOT_EXIST_ERROR("订单项不存在！"),
     * NULL_ADDRESS_ERROR("地址不能为空！"),
     * ORDER_PRICE_ERROR("订单价格异常！"),
     * <p>
     * ORDER_GENERATE_ERROR("生成订单异常！"),
     * <p>
     * SHOPPING_ITEM_ERROR("购物车数据异常！"),
     * <p>
     * SHOPPING_ITEM_COUNT_ERROR("库存不足！"),
     * <p>
     * ORDER_STATUS_ERROR("订单状态异常！"),
     */


    // 枚举实例的私有成员变量
    private Integer code;
    private String message;

    //枚举实例的私有构造方法

    private ExceptionCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    //获取枚举实例的成员值

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    //枚举类不要使用 setter，会导致原始常量改变。
//    public void setResult(String result) {
//        this.result = result;
//    }
}