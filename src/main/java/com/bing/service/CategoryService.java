package com.bing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.common.CustomException;
import com.bing.dao.CategoryDao;
import com.bing.entity.CategoryDO;
import com.bing.entity.DTO.CategoryDTO;
import com.bing.entity.DishDO;
import com.bing.entity.VO.CategoryVO;
import com.bing.util.MyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

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
     * 通过type 和 ID查询 列表
     *
     * @param type 类型
     * @return 实例对象
     */
    public List<CategoryDTO> queryByType(CategoryVO category) {
        LambdaQueryWrapper<CategoryDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(category.getType() != null, CategoryDO::getType, category.getType());
        lambdaQueryWrapper.orderByAsc(CategoryDO::getSort).orderByDesc(CategoryDO::getUpdateTime);
        List<CategoryDO> categoryDO = categoryDao.selectList(lambdaQueryWrapper);
        List<CategoryDTO> categoryDTO = MyBeanUtil.copyList(categoryDO, CategoryDTO.class, null);
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
        LambdaQueryWrapper<CategoryDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(CategoryDO::getSort);
        Page<CategoryDO> categoryDOPage = new Page<>(currentPage, pageSize);
        categoryDao.selectPage(categoryDOPage, lambdaQueryWrapper);
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
     * 根据 ID 修改数据,
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
     * 通过主键删除数据，
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
//        删除之前需要先判断是否关联有菜品，否则不予以删除
// 根据 分类ID 查询菜品表是否引用此分类
        DishDO dishDO = new DishDO();
        dishDO.setCategoryId(id);
        int countDish = dishService.count(dishDO);

        if (countDish > 0) {
            throw new CustomException("当前分类下关联了【菜品】，不能删除!");
        }

//        LambdaQueryWrapper<DishDO> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        dishLambdaQueryWrapper.eq(DishDO::getCategoryId, id);
        int countSetmeal = 0;
        if (countSetmeal > 0) {
            throw new CustomException("当前分类下关联了【套餐】，不能删除!");
        }

        return categoryDao.deleteByIdSql(id) > 0 ? true : false;
    }

}
