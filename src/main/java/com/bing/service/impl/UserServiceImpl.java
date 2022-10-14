package com.bing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bing.dao.UserDao;
import com.bing.entity.User;
import com.bing.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户信息(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-12 22:05:17
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

