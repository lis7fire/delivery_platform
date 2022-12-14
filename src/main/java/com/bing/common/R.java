package com.bing.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class R<T> implements Serializable {
    //    1: 成功， 其他： 失败
    private Integer code;
    //    错误信息 或者 success
    private String msg;
    //    错误时不显示 或 null
    private T data;
    //动态添加到响应体的数据,这里如果不new ，那么add时会报错 null
    private Map<String, Object> addedAttributes = new HashMap<String, Object>();

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> R<T> success(T data) {
        R<T> r = new R<T>();
        r.setCode(1);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    // 静态方法添加泛型：  static ＜T＞
    public static <T> R<T> fail(Integer code, String msg) {
        R<T> r = new R<T>();

        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> fail(ExceptionCodeEnum codeEnum) {
        R<T> r = new R<T>();

        r.setCode(codeEnum.getCode());
        r.setMsg(codeEnum.getMessage());
        return r;
    }

    /**
     * 响应体中临时增加一条属性
     *
     * @作者: LiBingYan
     * @时间: 2022/9/19
     */
    public R<T> addOneKV(String key, Object value) {
        this.addedAttributes.put(key, value);
        return this;
    }

}
