package com.bing.controller;

import com.bing.entity.DTO.EmployeeDTO;
import com.bing.entity.EmployeeDO;
import com.bing.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employee/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("login")
    public String employeeLogin(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.verifyUsernamePassword(employeeDTO);
        return "success";
    }

}
