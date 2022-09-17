package com.bing.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bing.entity.EmployeeDO;
import com.bing.mapper.EmployeeDAO;
import com.bing.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public void save() {

    }

    @Override
    public boolean save(EmployeeDO newEmployee) {
        return false;
    }

    @Override
    public boolean deleteById(Long bookId) {
        return false;
    }

    @Override
    public boolean update(EmployeeDO newEmployee) {
        return false;
    }

    @Override
    public EmployeeDO getById(Long bookId) {
        return null;
    }

    @Override
    public EmployeeDO verifyUsernamePassword(String username, String password) {
        LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<EmployeeDO>();
//      构造 queryWrapper 时，默认为 and 连接直接写，需要or时候才用 or()
        queryWrapper.eq(EmployeeDO::getUsername, username).eq(EmployeeDO::getPassword, password);
        EmployeeDO oneEmployee = employeeDAO.selectOne(queryWrapper);

        if (oneEmployee == null) {
            log.error("  查询不到,账号密码错误  ");
            return null;
        }
//        log.error(oneEmployee.toString());
        return oneEmployee;
    }


    @Override
    public List<EmployeeDO> getAll() {
        return null;
    }

    @Override
    public IPage<EmployeeDO> getByPage(Integer page, Integer psize) {
        return null;
    }
}
