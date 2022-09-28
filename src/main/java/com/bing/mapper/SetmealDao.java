package com.bing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bing.entity.SetmealDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 套餐(Setmeal)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-27 08:44:50
 */
public interface SetmealDao extends BaseMapper<SetmealDO> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Setmeal> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SetmealDO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Setmeal> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SetmealDO> entities);

}

