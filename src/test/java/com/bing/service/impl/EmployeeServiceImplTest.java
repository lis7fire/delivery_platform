package com.bing.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.entity.EmployeeDO;
import com.bing.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;


    @Test
    void verifyUsernamePassword() {
//        employeeService.verifyUsernamePassword("admin", "e10adc3949ba59abbe56e057f20f883e");
    }

    @Test
    void testPageSelect() {
        Page<EmployeeDO> onePage = employeeService.getByPageByName(1, 3, "   ");
        testSaveEdit(onePage.getRecords().get(0));
    }

    @Test
    void testSaveEdit(EmployeeDO employeeDO) {
//        employeeService.update(employeeDO);
        System.out.println("开始更新状态： ");
        employeeService.updateStatus(employeeDO.getEmployeeID(), 0);

    }


}