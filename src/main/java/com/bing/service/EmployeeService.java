package com.bing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bing.common.R;
import com.bing.service.DTO.EmployeeDTO;
import com.bing.entity.EmployeeDO;
import com.bing.mapper.EmployeeDAO;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

    public void save() {

    }

    public boolean save(EmployeeDO newEmployee) {
        return false;
    }

    public boolean deleteById(Long bookId) {
        return false;
    }

    public boolean update(EmployeeDO newEmployee) {
        return false;
    }

    public EmployeeDO getById(Long employeeId) {

        return employeeDAO.selectById(employeeId);
    }

    // 按照用户名查询员工信息
    public EmployeeDTO verifyUsernamePassword(EmployeeDTO employeeDTOin) {
        LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<EmployeeDO>();
        //      构造 queryWrapper 时，默认为 and 连接直接写，需要or时候才用 or()
        queryWrapper.eq(EmployeeDO::getUsername, employeeDTOin.getUsername());
        //                .eq(EmployeeDO::getPassword, employeeDTOin.getPassword())
        //                .eq(EmployeeDO::getStatus, 1);
        // 查询 DB时 无值就为null
        EmployeeDO oneEmployee = employeeDAO.selectOne(queryWrapper);
        if (oneEmployee == null) {
            return null;
        }

        EmployeeDTO employeeDTOout = new EmployeeDTO();
        MyBeanUtil.copyProperties(oneEmployee, employeeDTOout, null);
        return employeeDTOout;
    }


    public List<EmployeeDO> getAll() {
        return null;
    }

    public IPage<EmployeeDO> getByPage(Integer page, Integer psize) {
        return null;
    }
}
