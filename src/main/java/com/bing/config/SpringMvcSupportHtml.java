package com.bing.config;

import com.bing.interceptor.LoginStatusInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;


/**
 * MVC的核心配置类，也可以实现 implements WebMvcConfigurer 接口来完成相同功能，区别暂时未知
 *
 * @author: LiBingYan
 * @时间: 2022/9/20
 */
@Slf4j
@Configuration
public class SpringMvcSupportHtml extends WebMvcConfigurationSupport {

    @Autowired
    private LoginStatusInterceptor loginStatusInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        // 添加一个拦截器，拦截以 /admin 为前缀的url路径（后台登陆拦截）
        registry.addInterceptor(loginStatusInterceptor)
                .addPathPatterns("/employee/**") // 拦截的地址
                .excludePathPatterns("/employee/login")// 以下为排除的地址
                .excludePathPatterns("/employee/logout")
//                .excludePathPatterns("/backend/**")
                .excludePathPatterns("/front/**");

        log.info(" MVC 配置类 SpringMvcSupportHtml 中已添加了一个拦截器 ");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始配置静态资源映射：http://127.0.0.1/aaa 指向 resources/ttt/下面的资源");
        registry.addResourceHandler("/aaa/**").addResourceLocations("classpath:/ttt/");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/static/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/static/front/");
    }


}
