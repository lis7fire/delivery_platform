package com.bing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bing.common.ConstArgs;
import com.bing.common.R;
import com.bing.entity.ShoppingCart;
import com.bing.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 用户长期保存的购物车内容。
     *
     * @author LiBingYan
     * @时间 2022/10/15
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> getCarts(HttpSession session) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId).orderByDesc(ShoppingCart::getCreateTime);

        List<ShoppingCart> shoppingCartList = shoppingCartService.list(queryWrapper);
        return R.success(shoppingCartList);
    }

    //  清空购物车
    @DeleteMapping("/clean")
    public R<String> clean(HttpSession session) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }

    //    删除购物车中某一个
    @PostMapping("/sub")
    public R<String> deleteProdect(HttpSession session, @RequestBody ShoppingCart shoppingCart) {
        Long currentId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);
        shoppingCart.setUserId(currentId);

        LambdaUpdateWrapper<ShoppingCart> updateWrapper = new LambdaUpdateWrapper<>();
        //查询当前菜品或者套餐是否在购物车中
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        if (shoppingCart.getDishId() == null) {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
            updateWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        } else {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
            updateWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
        if (cartServiceOne.getNumber() < 2) {
//            删除本条购物车
            shoppingCartService.removeById(cartServiceOne.getId());
            return R.success(" 成功移除 ");
        } else {// 修改 number 列
            cartServiceOne.setNumber(cartServiceOne.getNumber() - 1);
            updateWrapper.set(ShoppingCart::getNumber, cartServiceOne.getNumber());
        }
        shoppingCartService.update(updateWrapper);
        return R.success(" 成功移除 ");
    }


    //向购物车添加一个
    @PostMapping("/add")
    public R<ShoppingCart> add(HttpSession session, @RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据:{}", shoppingCart);

        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);

        } else {
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        //查询当前菜品或者套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if (cartServiceOne != null) {
            //如果已经存在，就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);

            LambdaUpdateWrapper<ShoppingCart> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ShoppingCart::getId, cartServiceOne.getId());
            updateWrapper.set(ShoppingCart::getNumber, cartServiceOne.getNumber());
            shoppingCartService.update(updateWrapper);

//            shoppingCartService.updateById(cartServiceOne);
        } else {
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return R.success(cartServiceOne);
    }

}
