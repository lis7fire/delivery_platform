package com.bing.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.common.ConstArgs;
import com.bing.common.ExceptionCodeEnum;
import com.bing.common.R;
import com.bing.entity.DTO.CategoryDTO;
import com.bing.entity.DTO.SetmealDTO;
import com.bing.entity.SetmealDO;
import com.bing.entity.SetmealDish;
import com.bing.entity.VO.SetmealVO;
import com.bing.service.CategoryService;
import com.bing.service.SetmealDishService;
import com.bing.service.SetmealService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 套餐(Setmeal)表控制层
 *
 * @author makejava
 * @since 2022-10-01 22:54:32
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    /**
     * 服务对象
     */
    @Resource
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param setmeal 查询实体
     * @return 所有数据
     */
    @GetMapping(value = "/page")
    // MP的Page类无法接收，因为前端来的是PageSize名称 Page<SetmealDO> page,
    public R<Page> selectAll(SetmealVO setmeal) {
        //分页构造器对象
        Page<SetmealDO> onePage = new Page<>(setmeal.getPage(), setmeal.getPageSize());

        LambdaQueryWrapper<SetmealDO> queryWrapper = null;
        if (null != setmeal.getName()) {// 带条件查询,先去除空格，再设置条件
            String queryName = StringUtils.trimWhitespace(setmeal.getName());
            if (queryName.length() > 0) {
                queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.like(SetmealDO::getName, setmeal.getName());
                queryWrapper.orderByDesc(SetmealDO::getUpdateTime);
            }
        }
        setmealService.page(onePage, queryWrapper);

        //对象拷贝,DO -> DTO 响应给前端
        onePage.convert(oneSetmealDO -> {
            SetmealDTO setmealDTO = new SetmealDTO();
            MyBeanUtil.copyProperties(oneSetmealDO, setmealDTO);
            // 查询 套餐的种类名称
            CategoryDTO categoryDO = categoryService.queryById(setmealDTO.getCategoryId());
            if (categoryDO != null) {
                //分类名称
                setmealDTO.setCategoryName(categoryDO.getName());
            }
            return setmealDTO;
        });
        return R.success(onePage);
    }


    /**
     * 修改数据
     *
     * @param setmeal 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R<Boolean> update(HttpServletRequest request, @RequestBody SetmealDTO setmeal) {
        setmeal.setUpdateUser((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));
// 需要更新 套餐-菜品关系表
        setmealService.editOrSaveWithDish(false, setmeal);

        return R.success(true);
    }

    /**
     * []新增数据
     *
     * @param setmeal 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R<Boolean> insert(HttpServletRequest request, @RequestBody SetmealDTO setmealDto) {
        log.info("接收的setmeal: {}", setmealDto);
        setmealDto.setCreateUser((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));
        setmealDto.setUpdateUser((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));
//        套餐内的菜品 插入 关系表 setmeal_dish ，类似：菜品-口味关系表：dish_flavor
        setmealService.editOrSaveWithDish(true, setmealDto);
//        this.setmealService.save(setmeal);
        return R.success(true);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public R<SetmealDTO> selectOne(@PathVariable("id") Long setmealId) {
//        按照 ID 查询套餐详情
        SetmealDO setmealDO = setmealService.getById(setmealId);
        if (setmealDO == null) {
            return R.fail(ExceptionCodeEnum.SETMEAL_NOT_FOUND);
        }
        SetmealDTO setmealDTO = new SetmealDTO();
        MyBeanUtil.copyProperties(setmealDO, setmealDTO);

        // 查询套餐内包含的 菜品,
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealId);

        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDTO.setSetmealDishes(list);

        return R.success(setmealDTO);
    }

    /**
     * 批量起售禁售
     *
     * @param idsList 主键集合
     * @return
     * @author: LiBingYan
     * @时间: 2022/10/2
     */
    @PostMapping("/status/{status}")
    public R<String> editStatus(@PathVariable("status") int newStatus, @RequestParam(value = "ids") List<Long> idsList) {
        int count = setmealService.editBatchStatus(newStatus, idsList);
        String status = newStatus == 1 ? "启售" : "禁售";
        return R.success("成功" + status + "【 " + count + " 】个套餐");
    }

    /**
     * []批量 删除数据,物理删除，MySQL 中的关系表 设置了外键的删除级联规则，所以无需操作关系表
     *
     * @param setmealIds 主键集合
     * @return 删除结果
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> setmealIds) {
        int count = setmealIds.size();
//        setmealService.removeWithDish();  // 无需删除关系表中相关行，外键自动删除
        this.setmealService.removeByIds(setmealIds);
        return R.success("成功 删除【 " + count + " 】个套餐");
    }

    /**
     * 根据条件，获取套餐列表。应用于 前台系统
     *
     * @param
     * @return
     * @author: LiBingYan
     * @时间: 2022/10/12
     */
    @GetMapping("/list")
    public R<List<SetmealDO>> list(SetmealDO setmealDO) {
        LambdaQueryWrapper<SetmealDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != setmealDO.getCategoryId(), SetmealDO::getCategoryId, setmealDO.getCategoryId());
        queryWrapper.eq(SetmealDO::getStatus, 1);
        queryWrapper.orderByDesc(SetmealDO::getCreateTime);
        return R.success(setmealService.list(queryWrapper));
    }

    // 查询套餐下面拥有的 菜品
    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> list(@PathVariable("id") Long setmealId) {
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
        queryWrapper.eq(SetmealDish::getIsDeleted, 0);
        queryWrapper.orderByAsc(SetmealDish::getSort);

        return R.success(setmealDishService.list(queryWrapper));
    }

}