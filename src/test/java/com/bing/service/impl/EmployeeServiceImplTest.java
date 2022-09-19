package com.bing.service.impl;

import com.bing.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void save() {
    }

    @Test
    void verifyUsernamePassword() {
//        employeeService.verifyUsernamePassword("admin", "e10adc3949ba59abbe56e057f20f883e");
    }
}