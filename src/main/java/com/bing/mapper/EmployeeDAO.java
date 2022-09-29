package com.bing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bing.entity.EmployeeDO;

//在 mapper 包下面的无需注解，也无需 @MapperScan ，应该是mybatis的自动配置中默认会扫描 Mapper。
//@Mapper
public interface EmployeeDAO extends BaseMapper<EmployeeDO> {
    public EmployeeDO selectOneUseXML(String name);

    public int updateStatus(Long employeeId, Integer status);

}
