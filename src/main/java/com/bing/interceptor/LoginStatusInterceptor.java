package com.bing.interceptor;


import com.bing.util.MatchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

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

//        1.获取本次请求的URI
        String path = request.getServletPath();
        log.info("MVC 拦截器 拦截的 path： {}", path);

        String[] ignoreUrls = new String[]{"/employee/login",  // 使用拦截器时，此列表作废，在 MVC 核心配置中设置
                "/employee/logout",
                "/backend/**",
                "/front/**"};

        //        4.判断登录状态，session中已经登录，放行
        if (request.getSession().getAttribute("employeeId_session") != null) {
            return true;
        }
//
//        3.未登录，但是无需处理，放行
        if (MatchUtil.check(path, ignoreUrls)) {
//      访问的是忽略列表的地址，不拦截
            return true;
        }

//        5.未登录，且访问的是需要权限的页面，重定向到登录页面
        System.out.println(request.getRemoteHost() +"       根路径");
        response.sendRedirect(request.getContextPath() + "backend/page/login/login.html");
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
