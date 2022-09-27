package com.bing.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.common.R;
import com.bing.entity.DishDO;
import com.bing.service.DishService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 菜品管理(Dish)表控制层
 *
 * @author makejava
 * @since 2022-09-26 22:23:52
 */
@RestController
@RequestMapping("dish")
public class DishController   {
    /**
     * 服务对象
     */
    @Resource
    private DishService dishService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param dish 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<DishDO> page, DishDO dish) {
        return R.success(this.dishService.page(page, new QueryWrapper<>(dish)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.success(this.dishService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param dish 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody DishDO dish) {
        return R.success(this.dishService.save(dish));
    }

    /**
     * 修改数据
     *
     * @param dish 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody DishDO dish) {
        return R.success(this.dishService.updateById(dish));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return R.success(this.dishService.removeByIds(idList));
    }
}

