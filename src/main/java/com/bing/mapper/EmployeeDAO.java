package com.bing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bing.entity.EmployeeDO;
import org.apache.ibatis.annotations.Mapper;

public interface EmployeeDAO extends BaseMapper<EmployeeDO> {
    public EmployeeDO selectOneUseXML(String name);

    public int updateStatus(Long employeeId,Integer status);

}
