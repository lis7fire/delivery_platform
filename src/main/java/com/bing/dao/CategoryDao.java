package com.bing.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bing.entity.CategoryDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
//import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 菜品及套餐分类(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-26 22:12:42
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryDO> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CategoryDO queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param category 查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
//    List<Category> queryAllByLimit(Category category, @Param("pageable") Pageable   pageable);

    /**
     * 统计总行数
     *
     * @param category 查询条件
     * @return 总行数
     */
    long count(CategoryDO category);

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 影响行数
     */
    int insert(CategoryDO category);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Category> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CategoryDO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Category> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<CategoryDO> entities);

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 影响行数
     */
    int update(CategoryDO category);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

