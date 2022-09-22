package com.bing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.bing.common.R;
import com.bing.controller.VO.EmployeeVO;
import com.bing.service.DTO.EmployeeDTO;
import com.bing.entity.EmployeeDO;
import com.bing.mapper.EmployeeDAO;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * -------------------------------------------------
     * 分页查询所有员工，可选按照 姓名 查
     *
     * @params: 若传入姓名，则按照姓名查询
     * @return: 携带 DO的 page
     * @author: LiBingYan
     * @时间: 2022/9/21
     */
    public Page<EmployeeDO> getByPageByName(Integer currenPage, Integer psize, String name) {
        // 构造查询条件： name 有值，就按name查，否则 查询全部，
        LambdaQueryWrapper<EmployeeDO> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.likeRight(StringUtils.hasLength(name), EmployeeDO::getName, name);
        // 以 更新时间 降序
        queryWrapper.orderByDesc(EmployeeDO::getUpdate_time);
        // 采用 MP的分页查询， MP的分页查询效率低，最好自己优化实现，不用MP的此插件
        Page<EmployeeDO> onePage = new Page<>(currenPage, psize);
        employeeDAO.selectPage(onePage, queryWrapper);

        return onePage;
    }

}
