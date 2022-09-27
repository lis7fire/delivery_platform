package com.bing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.entity.CategoryDO;
import org.springframework.stereotype.Service;

/**
 * 菜品及套餐分类(Category)表服务接口
 *
 * @author makejava
 * @since 2022-09-26 22:12:53
 */
@Service
public class CategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CategoryDO queryById(Long id) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param category    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
//    Page<CategoryDO> queryByPage(CategoryDO category, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    CategoryDO insert(CategoryDO category) {
        return null;
    }

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    CategoryDO update(CategoryDO category) {
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id) {
        return false;
    }

}
