package com.bing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// 在 mapper 包下面的无需注解，也无需 @MapperScan ，应该是mybatis的自动配置中默认会扫描 Mapper。
@MapperScan(basePackages = {"com.bing.dao"}) //,"com.bing.mapper"
@EnableTransactionManagement
@EnableCaching
public class NutritionMealApplication {

    public static void main(String[] args) {
        SpringApplication.run(NutritionMealApplication.class, args);
        System.out.println("项目启动成功...");
        ApplicationContext applicationContext;

//        Environment environment = applicationContext.getEnvironment();
    }


}
