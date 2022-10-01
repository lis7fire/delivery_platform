package com.bing.service;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class DishServiceTest {

    @Test
    void saveWithFlavor() {

        System.out.println("ceshi");

        UUID aa = UUID.randomUUID();
        String uuidStr = aa.toString();
        String cc=  uuidStr.replace("-", "");
        Integer userId = uuidStr.hashCode();
        // String.hashCode()可能会是负数，如果为负数需要转换为正数
        userId = userId < 0 ? -userId : userId;
        Long.valueOf(String.valueOf(userId));



    }
}