package com.bing.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 兜底异常处理，利用 @Order(value = 1) 让此类后加载，以便兜底
 * 多个类文件通过 @Order 来确定异常异常匹配的顺序，
 * 每个类内部通过 框架的 ExceptionHandlerMethodResolver#getMappedMethod 中的默认顺序来匹配，用户无法干预
 *
 * @author: LiBingYan
 * @时间: 2022/9/26
 */
@RestControllerAdvice
@Slf4j
@Order(value = 1000)
public class GlobalRootExceptionAdviceHandler {
    /**
     * 未捕获到的所有异常,作为系统异常;
     *
     * @param ex 异常
     * @return 响应体
     * @注意： 必须把此根异常放在最前面，因为 @ExceptionHandler 注解是 栈 的形式入栈的，最后的代码在最前面匹配。
     */
    @ExceptionHandler(value = Exception.class)
    public R<String> rootExceptionHandler(Exception ex) {
        log.error("发生根异常错误", ex);
        return R.fail(400, "服务器错误，请等待后重试！");
    }

}
