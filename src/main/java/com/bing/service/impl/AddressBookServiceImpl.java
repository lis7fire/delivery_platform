package com.bing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bing.dao.AddressBookDao;
import com.bing.entity.AddressBook;
import com.bing.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * 地址管理(AddressBook)表服务实现类
 *
 * @author makejava
 * @since 2022-10-17 10:43:40
 */
@Service("addressBookService")
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBook> implements AddressBookService {

}

