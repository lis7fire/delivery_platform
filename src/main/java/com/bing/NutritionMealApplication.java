package com.bing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.bing.mapper"})
public class NutritionMealApplication {

    public static void main(String[] args) {
        SpringApplication.run(NutritionMealApplication.class, args);
        System.out.println("项目启动成功...");
    }

}
