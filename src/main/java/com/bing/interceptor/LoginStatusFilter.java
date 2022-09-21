package com.bing.interceptor;

import com.alibaba.fastjson2.JSON;
import com.bing.common.R;
import com.bing.util.MatchUtil;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器(Filter):       属于servlet 技术。对所有访问增强。
 * 拦截器(Interceptor): 属于 MVC 技术。   只对SpringMVC的访问增强（即controller声明的访问 ）。
 *
 * @author: LiBingYan
 * @时间: 2022/9/20
 */
@Slf4j
@Component // 过滤器必须成为Bean
@WebFilter(filterName = "loginStatusFilter", urlPatterns = "/*")
public class LoginStatusFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        1.获取本次请求的URI
        String uri = request.getRequestURI();
        log.info(" 登录状态验证 过滤器 Filter 开始处理：,拦截的 URI： {} ", uri);
//      以下的静态页面等不需登录，但是其他的 api 必须登录。这里只是 员工角色，还未过滤用户的权限
        String[] ignoreUrls = new String[]{"/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"};

//        2.判断本次请求地址是否需要处理
        boolean check = MatchUtil.check(uri, ignoreUrls);
//        3.若无需处理，放行  4.判断登录状态，session中已经登录，放行
        if (check || request.getSession().getAttribute("employeeId_session") != null) {
            chain.doFilter(request, response); // 放行，向下传递
            return;
        }

//        5.若未登录，响应通知前端，（这里还未到 MVC ，所以只能 write 写流）
        response.getWriter().write(JSON.toJSONString(R.fail(400, "未登录，无操作权限！")));
        return;
    }


}
