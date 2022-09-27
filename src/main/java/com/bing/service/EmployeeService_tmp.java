package com.bing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bing.entity.DTO.EmployeeDTO;
import com.bing.entity.EmployeeDO;

import java.util.List;

/**
 * 系统较小，直接用业务层的实现，不要接口，后期稳定了再用
 *
 * @作者: LiBingYan
 * @时间: 2022/9/19
 */
public interface EmployeeService_tmp {
    /**
     * -------------------------------------------------
     *
     * @功能: 针对 员工 增删改查 服务
     * @作者: LiBingYan
     * @时间: 2022/9/16
     * --------------------------------------------------
     */
    public void save();

    public boolean save(EmployeeDO newEmployee);

    public boolean deleteById(Long bookId);

    public boolean update(EmployeeDO newEmployee);

    public EmployeeDO getById(Long bookId);

    public EmployeeDTO verifyUsernamePassword(EmployeeDTO employeeDTO);

    public List<EmployeeDO> getAll();

    public IPage<EmployeeDO> getByPage(Integer page, Integer psize);
}
