package com.bing.interceptor;


import com.alibaba.fastjson2.JSON;
import com.bing.common.ConstArgs;
import com.bing.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台系统身份验证拦截器, 与 LoginStatusFilter 功能一样，重复实验一下
 *
 * @author: LiBingYan
 * @时间: 2022/9/20
 */
@Component
@Slf4j
public class LoginStatusInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//拦截器：拦截规则在 配置类中 registry.addInterceptor() 方法设置，
// 所以 只要是进入此方法的，一定是符合拦截规则的请求

//        1.获取本次请求的URI
        String path = request.getServletPath();
//        前台已登录，放行，
        if (null != request.getSession().getAttribute(ConstArgs.USERID_SESSION)) {
            log.info("MVC 拦截器 拦截了【前台】 path： {}； === 结果 ===:    放行", path);
            return true;
        }
        // 2.判断后台登录状态，session中已经登录，放行
        if (null != request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION)) {
            log.info("MVC 拦截器 拦截了【后台】 path： {}； === 结果 ===:    放行", path);

            //客户端每次发送的请求都对应一个线程。
            Long threadId = Thread.currentThread().getId();
            log.info("对应线程id：{}", threadId);
            ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

            return true;
        }

//        3.未登录，且访问的是需要权限的页面，重定向到登录页面
        log.info("MVC 拦截器 拦截了 path： {}； === 结果 ===:    拦截，响应通知前端重定向", path);
        response.setCharacterEncoding("UTF-8"); //设置 HttpServletResponse使用utf-8编码
        response.setHeader("Content-Type", "application/json;charset=utf-8");  //设置响应头的编码
        response.getWriter().write(JSON.toJSONString(R.fail(400, "未登录，无操作权限！")));

//        response.sendRedirect( "../backend/page/login/login.html"); 作废
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
