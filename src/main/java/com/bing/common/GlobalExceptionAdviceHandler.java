package com.bing.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器: 在这里统一处理系统内所有异常
 *
 * @author: LiBingYan
 * @时间: 2022/9/26
 */
@RestControllerAdvice(basePackages = "com.bing")
@Slf4j
@Order(value = 1)
public class GlobalExceptionAdviceHandler {
    // 业务异常由具体异常返回提示信息，系统异常统一返回
    @ExceptionHandler(value = IllegalStateException.class)
    public R<String> requestExceptionHandler(IllegalStateException ex) {
        log.error("业务异常,请求 参数名称 无法匹配：", ex);
        return R.fail(ExceptionCodeEnum.REQUEST_PARAMETER_NOT_MATCH);
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    public R<String> requestMessageExceptionHandler(HttpMessageConversionException ex) {
        log.error("业务异常,请求 参数值类型 无法匹配：：", ex);
        return R.fail(ExceptionCodeEnum.REQUEST_PARAMETER_VALUE_NOT_MATCH);
    }

    // 请求参数的判断： URL参数格式非法，path参数格式非法。
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public R<String> rootExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.error("请求参数校验错误", ex);
        return R.fail(400, "数据格式错误，请检查输入！");
    }

    @ExceptionHandler(value = SQLException.class)
    public R<String> dataBaseExceptionHandler(SQLException ex) {
        log.error("SQL数据库执行异常：", ex);
        String msg = "参数有误，请检查输入";
        return R.fail(ExceptionCodeEnum.DB_ERROR);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public R<String> dbKeyExceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error("主键重复，无法插入：", ex);
        String msg = "";
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            msg = split[2] + "已存在";
        } else msg = "发生未知异常。";
        return R.fail(400, msg);
    }

}
