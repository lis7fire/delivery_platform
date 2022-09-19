package com.bing.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * controller 层对外响应的封装类,
 *
 * @作者: LiBingYan
 * @时间: 2022/9/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {
    //    1: 成功， 其他： 失败
    private Integer code;
    //    错误信息 或者 success
    private String msg;
    //    错误时不显示 或 null
    private Object data;
    //动态添加到响应体的数据,这里如果不new ，那么add时会报错 null
    private Map<String, Object> addedAttributes = new HashMap<String, Object>();

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static R success(Object data) {
        R r = new R();
        r.setCode(1);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    public static R fail(Integer code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    /**
     * 响应体中临时增加一条属性
     *
     * @作者: LiBingYan
     * @时间: 2022/9/19
     */
    public R addOneKV(String key, Object value) {
        this.addedAttributes.put(key, value);
        return this;
    }

}
