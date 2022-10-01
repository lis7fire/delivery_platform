package com.bing.mapper;

import com.bing.entity.DishDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品管理(DishDO)表数据库访问层。实验：Dish 的dao层全部使用原生的SQL查询，不用MP
 *
 * @author BingYan
 * @since 2022-09-29 13:52:47
 */
public interface DishDao {

    /**
     * 通过ID查询单条详细数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DishDO queryByIdSql(Long id);


    /**
     * 无条件，分页查询，所有菜品
     * 每页查询语句为： limit (currentPage - 1)* pageSize , pageSize;
     * 其中 ：startIndex = (currentPage - 1)* pageSize
     *
     * @param startIndex 分页信息
     * @param pageSize   分页信息
     * @return 对象列表
     */
    List<DishDO> queryAllByLimitPageSql(int startIndex, int pageSize);


    /**
     * 按条件，分页查询，指定行数据
     * 每页查询语句为： limit (currentPage - 1)* pageSize , pageSize;
     * 其中 ：startIndex = (currentPage - 1)* pageSize
     *
     * @param DishDO   查询条件
     * @param startIndex 分页信息
     * @return 对象列表
     */
    List<DishDO> queryByLimitPageSql(@Param("DishDO") DishDO DishDO, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);


    /**
     * 有条件统计总行数
     *
     * @param DishDO 查询条件
     * @return 总行数
     */
    int countSql(DishDO DishDO);

    /**
     * -------------------------------------------------
     * 无条件统计总行数，只规定删除状态作为条件
     *
     * @params: 是否删除，1已删除，0未删除,null为包括删除和未删除
     * @return: 总行数
     * @author: LiBingYan
     * @时间: 2022/9/29
     */
    long countAllSql(@Param("isDeleted") Integer isDeleted);

    /**
     * 新增数据
     *
     * @param DishDO 实例对象
     * @return 影响行数
     */
    int insertSql(DishDO DishDO);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DishDO> 实例对象列表
     * @return 影响行数
     */
    int insertBatchSql(@Param("entities") List<DishDO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DishDO> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatchSql(@Param("entities") List<DishDO> entities);

    /**
     * 修改数据
     *
     * @param DishDO 实例对象
     * @return 影响行数
     */
    int updateSql(DishDO DishDO);

    /**
     * 通过主键【真实】删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteByIdSql(Long id);

    /**
     * 通过主键【逻辑】批量 删除
     *
     * @param id 主键
     * @return 影响行数
     * @author LiBingYan
     * @时间 2022/9/29
     */
    int editDeleteByIdSql(List<Long> dishIdList);
//    如果 id未空？

    /**
     * -------------------------------------------------
     * 通过主键修改 上架/下架 状态
     *
     * @param
     * @return
     * @author: LiBingYan
     * @时间: 2022/9/29
     */
    int editStatusByIdSql(List<Long> dishIdList, Integer newStatus);
//    如果 id未空？

}

