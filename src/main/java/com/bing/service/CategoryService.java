package com.bing.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.dao.CategoryDao;
import com.bing.entity.CategoryDO;
import com.bing.entity.DTO.CategoryDTO;
import com.bing.util.MyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜品及套餐分类(Category)表服务接口
 *
 * @author makejava
 * @since 2022-09-26 22:12:53
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public CategoryDTO queryById(Long id) {
        CategoryDO categoryDO = categoryDao.selectById(id);
        CategoryDTO categoryDTO = new CategoryDTO();
        MyBeanUtil.copyProperties(categoryDO, categoryDTO);
        return categoryDTO;
    }

    /**
     * 分页查询
     *
     * @param category    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    public Page<CategoryDO> queryByPage(CategoryDTO category, int currentPage, int pageSize) {
        Page<CategoryDO> categoryDOPage = new Page<>(currentPage, pageSize);
        categoryDao.selectPage(categoryDOPage, null);
        return categoryDOPage;
    }

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    public boolean insert(CategoryDTO category) {

        CategoryDO categoryDO = new CategoryDO();
        MyBeanUtil.copyProperties(category, categoryDO);
        boolean result = categoryDao.insert(categoryDO) > 0 ? true : false;
        return result;
    }

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    public boolean update(CategoryDTO category) {
        CategoryDO categoryDO = new CategoryDO();
        MyBeanUtil.copyProperties(category, categoryDO);
        boolean result = categoryDao.updateById(categoryDO) > 0 ? true : false;
        return result;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        return categoryDao.deleteByIdSql(id) > 0 ? true : false;
    }

}
