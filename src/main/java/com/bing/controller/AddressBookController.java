package com.bing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bing.common.ConstArgs;
import com.bing.common.ExceptionCodeEnum;
import com.bing.common.R;
import com.bing.entity.AddressBook;
import com.bing.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;

    /**
     * 查询用户的全部地址，按照默认地址排序
     *
     * @param
     * @return
     * @author: LiBingYan
     * @时间: 2022/10/17
     */
    @GetMapping("/list")
    public R<List<AddressBook>> getAddressLists(HttpSession session) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getIsDeleted, 0);
        queryWrapper.orderByDesc(AddressBook::getIsDefault).orderByDesc(AddressBook::getCreateTime);
        List<AddressBook> addressBookList = addressBookService.list(queryWrapper);

        return R.success(addressBookList);
    }

    //根据 ID 查询地址详情以便修改
    @GetMapping("/{id}")
    public R<AddressBook> get(HttpSession session, @PathVariable Long id) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getId, id);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (addressBook == null) {
            return R.fail(ExceptionCodeEnum.ADDRESS_NOT_FOUND);
        }
        return R.success(addressBook);
    }

    //查询默认地址,用于下订单
    @GetMapping("/default")
    public R<AddressBook> getDefault(HttpSession session) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getIsDeleted, 0);
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        //SQL:select * from address_book where user_id = ? and is_default = 1 and id_delete = 0
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (null == addressBook) { //"没有找到默认地址"
            return R.fail(ExceptionCodeEnum.ADDRESS_NOT_FOUND);
        } else {
            return R.success(addressBook);
        }
    }

    //新增地址
    @PostMapping
    public R<AddressBook> add(HttpSession session, @RequestBody AddressBook addressBook) {
        log.info("接收到的新增地址：{}", addressBook);
//        测试电话格式是否正确
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);
        addressBook.setUserId(userId);
        addressBook.setCreateUser(userId);
        addressBook.setUpdateUser(userId);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    //修改地址
    @PutMapping
    public R<String> edit(HttpSession session, @RequestBody AddressBook addressBook) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);
        addressBook.setUpdateUser(userId);
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getId, addressBook.getId());
        addressBookService.update(addressBook, wrapper);
        return R.success("地址修改成功");
    }

    //    设置默认地址
    @PutMapping("/default")
    public R<String> editDefault(HttpSession session, @RequestBody AddressBook addressBook) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);
// 将所有地址置为 非默认
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<AddressBook>();
        wrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getIsDefault, 1);
        wrapper.set(AddressBook::getIsDefault, 0);
        addressBookService.update(wrapper);
//        将指定地址 设为 默认
        wrapper.clear();
        wrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getId, addressBook.getId());
        wrapper.set(AddressBook::getIsDefault, 1);
        addressBookService.update(wrapper);

        return R.success("默认地址设置成功");
    }
//逻辑删除一个地址
    @DeleteMapping
    public R<String> del(HttpSession session, @RequestParam("ids") Long id) {
        Long userId = (Long) session.getAttribute(ConstArgs.USERID_SESSION);

        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getId, id);
        wrapper.set(AddressBook::getIsDeleted, 1);

        addressBookService.update(wrapper);
        return R.success(" 地址删除成功");
    }

}