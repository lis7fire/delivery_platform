package com.bing.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
// Boot 将 SSM 整合了，所以不需要写 mybatis的核心配置类，使用yml配置。
public class MybatisCoreConf {
//    @Bean
    public MybatisSqlSessionFactoryBean getMPSession() {
        MybatisSqlSessionFactoryBean mpSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mpSqlSessionFactoryBean.setTypeAliasesPackage("com/bing/entity");
        return mpSqlSessionFactoryBean;
    }
}
