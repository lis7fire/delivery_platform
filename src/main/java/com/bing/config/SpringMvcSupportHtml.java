package com.bing.config;

import com.bing.common.JacksonMessageObjectMapper;
import com.bing.interceptor.LoginStatusInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;


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
        // 添加一个 MVC拦截器，拦截以 /admin 为前缀的url路径（后台登陆拦截）
        registry.addInterceptor(loginStatusInterceptor)
                .addPathPatterns("/employee/**") // 拦截的地址,员工管理
                .addPathPatterns("/shoppingCart/**")
//                .addPathPatterns("/category/**")// 拦截的地址,分类管理
//                .addPathPatterns("/dish/**")// 拦截的地址,菜品管理
//                .addPathPatterns("/setmeal/**")// 拦截的地址,套餐管理
//                .addPathPatterns("/order/**")// 拦截的地址,订单管理
                // 以下为排除的地址,只针对 @controller 注解的地址有效
                .excludePathPatterns("/employee/login")
                .excludePathPatterns("/employee/logout")
                .excludePathPatterns("/user/sendMsg")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/logout")
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

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("初始化  扩展的消息转换器，开始...{}", converters.get(7) instanceof MappingJackson2HttpMessageConverter);
//        创建 消息转换器 对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

//         配置 消息转换器底层使用的 类型映射器 为我们自定义的映射器。
        messageConverter.setObjectMapper(new JacksonMessageObjectMapper());
//        System.out.println(messageConverter.getDefaultCharset()); null
        messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
//        将上面配置好的 消息转换器 追加到 MVC框架的默认转换器集合中，并且置顶，否则不会生效
        converters.add(0, messageConverter);
    }
}
