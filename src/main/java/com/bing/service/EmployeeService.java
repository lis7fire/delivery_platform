package com.bing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bing.entity.EmployeeDO;

import java.util.List;

public interface EmployeeService {
/**-------------------------------------------------
 * @功能:     针对 员工 增删改查 服务
 *
 * @作者:  LiBingYan
 * @时间:  2022/9/16
 *--------------------------------------------------
 */
    public void save();

    public boolean save(EmployeeDO newEmployee);

    public boolean deleteById(Long bookId);

    public boolean update(EmployeeDO newEmployee);

    public EmployeeDO getById(Long bookId);

    EmployeeDO verifyUsernamePassword(String username, String password);

    public List<EmployeeDO> getAll();

    public IPage<EmployeeDO> getByPage(Integer page, Integer psize);
}
