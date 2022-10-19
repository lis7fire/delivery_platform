package com.bing.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.common.ConstArgs;
import com.bing.common.R;
import com.bing.entity.DTO.DishDTO;
import com.bing.entity.DishDO;
import com.bing.entity.DishFlavorDO;
import com.bing.entity.VO.DishVO;
import com.bing.service.DishFlavorService;
import com.bing.service.DishService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理(DishDO)表控制层
 *
 * @author makejava
 * @since 2022-09-29 09:21:57
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    /**
     * 服务对象
     */
    @Resource
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 完成-待优化分页查询所有数据
     *
     * @param dish 分页对象
     * @param dish 查询实体
     * @return 所有数据
     */
    @GetMapping("/page") // Page<DishDO> page, DishDO DishDO
    public R<Page<DishDO>> selectByPage(HttpServletRequest request, DishVO dish) {
        log.info(String.valueOf(dish));
//        request.getQueryString()
        request.getRequestURL();
        Page<DishDO> onePage = dishService.queryByPage(dish);

//        return R.success(this.dishService.page(page, new QueryWrapper<>(DishDO)));
        return R.success(onePage);
    }

    /**
     * 查询某种类型的 菜品列表,套餐模块使用，前台也使用
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/list")
    public R<List<DishDTO>> selectOne(@RequestParam("categoryId") Long categoryId) {
        log.info("需要查询的菜品种类为：{}", categoryId);
        List<DishDO> dishs = this.dishService.queryBycategoryId(categoryId);

        List<DishDTO> dishDTOList =   dishs.stream().map((item) -> {
            DishDTO dishDto = new DishDTO();
            MyBeanUtil.copyProperties(item, dishDto);
            Long category = item.getCategoryId();//分类id
            //根据id查询分类对象
//               查询某个菜品的口味
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavorDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavorDO::getDishId, dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavorDO> dishFlavorList = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDTOList);
    }


    /**
     * 完成-通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public R<DishDTO> selectOne(@PathVariable("id") long id) {
        return R.success(dishService.getInfoByIdWithFlavor(id));
    }

    /**
     * 完成-新增数据
     *
     * @param Dish 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R<String> insert(HttpServletRequest request, @RequestBody DishDTO Dish) {
        log.info("新增接收的数据：{}", Dish);
        setTimeUser(request, Dish, true);
        dishService.saveWithFlavor(Dish);
        return R.success("新增菜品： " + Dish.getName() + " 成功");
    }

    /**
     * 完成-修改数据
     *
     * @param Dish 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody DishDTO Dish) {
        setTimeUser(request, Dish, false);
        dishService.updateWithFlavor(Dish);
        return R.success("修改菜品： " + Dish.getName() + " 成功");
    }

    /**
     * 完成-【批量】删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> idList) {
        dishService.removeByIds(idList);
        return R.success("菜品批量删除成功！");
    }


    /**
     * 完成- 起售/停售 - 批量起售停售
     *
     * @param idList 主键列表
     * @return 删除结果
     */
    @PostMapping("/status/{newStatus}")
    public R<String> stopSelling(@RequestParam("ids") List<Long> idList, @PathVariable int newStatus) {
        dishService.editStatus(idList, newStatus);
        return R.success("菜品状态更新成功！");
    }


    private void setTimeUser(HttpServletRequest request, DishDO entityDo, boolean isInsert) {
        request.getSession().setAttribute(ConstArgs.EMPLOYEE_ID_SESSION, 1L);
        if (isInsert) {
            entityDo.setCreateTime(LocalDateTime.now());
            entityDo.setCreateUser((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));
        }
        entityDo.setUpdateTime(LocalDateTime.now());
        entityDo.setUpdateUser((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));
    }

}

