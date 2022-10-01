package com.bing.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.entity.DTO.DishDTO;
import com.bing.entity.DishDO;
import com.bing.entity.DishFlavorDO;
import com.bing.entity.VO.DishVO;
import com.bing.mapper.DishDao;
import com.bing.util.MyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理(Dish)表服务实现类
 *
 * @author makejava
 * @since 2022-09-29 09:21:58
 */
@Service("dishService")
public class DishService {

    @Autowired
    private DishDao dishDao;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 按条件，分页查询 菜品
     *
     * @param dishVO 条件和分页数据
     * @return
     * @author: LiBingYan
     * @时间: 2022/9/29
     */
    public Page<DishDO> queryByPage(DishVO dishVO) {

        DishDO dishDO = new DishDO();
        MyBeanUtil.copyProperties(dishVO, dishDO);
// 这里应防止 dishVO 中值为null
        int pageSize = dishVO.getPageSize();
        int startIndex = (dishVO.getPage() - 1) * pageSize;
//        判断是查所有还是按照条件查询
        List<DishDO> page = null;
        long count;
        if (dishDO.getName() == null) {
            count = dishDao.countAllSql(0);
            page = dishDao.queryAllByLimitPageSql(startIndex, pageSize);
        } else {
            dishDO.setIsDeleted(0);
            count = dishDao.countSql(dishDO);
            page = dishDao.queryByLimitPageSql(dishDO, startIndex, pageSize);
        }
        Page<DishDO> onePage = new Page<>(dishVO.getPage(), pageSize, count);
        onePage.setRecords(page);

        return onePage;
    }

    /**
     * 按 ID 查询单个详情
     *
     * @param id 一个主键
     * @return 一条数据
     * @author: LiBingYan
     * @时间: 2022/9/29
     */
    public DishDO queryById(long id) {
        DishDO dishDO = dishDao.queryByIdSql(id);
        return dishDO;
    }

    //根据id查询菜品信息和对应的口味信息
    public DishDTO getByIdWithFlavor(Long dishId) {
        DishDO dish = dishDao.queryByIdSql(dishId);
        DishDTO dishDTO = new DishDTO();
        MyBeanUtil.copyProperties(dish, dishDTO);
// 查询菜品口味列表
        LambdaQueryWrapper<DishFlavorDO> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DishFlavorDO::getDishId, dishId);
        List<DishFlavorDO> flavorList = dishFlavorService.list(lambdaQueryWrapper);
        dishDTO.setFlavors(flavorList);

        return dishDTO;
    }

    //更新菜品信息，同时更新对应的口味信息
    @Transactional
    public boolean updateWithFlavor(DishDTO dishDto) {
//        更新dish表的数据
        dishDao.updateSql(dishDto);
//        更新 口味表 dish_flavor 的列表数据

        //先删除旧的口味
        LambdaQueryWrapper<DishFlavorDO> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DishFlavorDO::getDishId, dishDto.getId());
        dishFlavorService.remove(lambdaQueryWrapper);
        //再添加新的口味
        List<DishFlavorDO> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            item.setUpdateUser(dishDto.getUpdateUser());
            item.setCreateUser(dishDto.getUpdateUser());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch((flavors));

        return false;

    }

    /**
     * 新增一个菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
     *
     * @param dish 新增的实体
     * @return 是否成功 ， 不成功会进入异常处理，所以这里一定返回true
     * @author: LiBingYan
     * @时间: 2022/9/29
     */
    @Transactional
    public boolean saveWithFlavor(DishDTO dish) {
//        String uuidStr = UUID.randomUUID().toString().replace("-", "");
//        Long newDishId= Long.valueOf(uuidStr);
//        dish.setId(newDishId);
// 保存菜品基本信息入库
        dishDao.insertSql(dish);
        Long dishId = dish.getId();
        Long currentUser = dish.getUpdateUser();
//        菜品口味/偏好处理
        List<DishFlavorDO> flavorList = dish.getFlavors();
        flavorList = flavorList.stream().map((item) -> {
            item.setDishId(dishId);
            item.setCreateUser(currentUser);
            item.setUpdateUser(currentUser);
            return item;
        }).collect(Collectors.toList());

//        保存菜品口味到口味表 dish_flavor
        dishFlavorService.saveBatch(flavorList);
        return true;
    }

    /**
     * 批量新增菜品
     *
     * @param dishs 新增的实体 列表
     * @return 是否成功
     * @author: LiBingYan
     * @时间: 2022/9/29
     */
    public boolean saveBatchs(List<DishDO> dishs) {
        dishDao.insertBatchSql(dishs);
        return true;
    }

    /**
     * 启售/停售  可以批量操作
     *
     * @param newStats 0 停售 1 起售
     * @return
     * @author: LiBingYan
     * @时间: 2022/9/29
     */
    public boolean editStatus(List<Long> idList, int newStats) {
        dishDao.editStatusByIdSql(idList, newStats);
        return true;
    }

    /**
     * 按照 id 列表 [批量] 删除，
     *
     * @param idList id 列表
     * @return 成功
     * @author: LiBingYan
     * @时间: 2022/9/29
     */
    public boolean removeByIds(List<Long> idList) {
//        删除的同时要把status 下架
        dishDao.editDeleteByIdSql(idList);
        return true;
    }

//    查询满足条件的行数
    public int count(DishDO dishDO) {
        return  dishDao.countSql(dishDO);
    }
}

