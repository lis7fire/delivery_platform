package com.bing.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bing.common.ConstArgs;
import com.bing.common.ExceptionCodeEnum;
import com.bing.common.R;
import com.bing.entity.User;
import com.bing.service.UserService;
import com.bing.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * 用户信息(User)表控制层
 *
 * @author makejava
 * @since 2022-10-12 22:05:16
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    @PostMapping("/login")
    public R<String> frontUserLogin(HttpSession session, @RequestBody Map phoneCode) {
//        log.info(phoneCode.toString());
        //        后期加入密码登录
        String password = (String) phoneCode.get("password");
        phoneCode.get("phone");

//        提取手机号 + 验证码 并且验证 , 转换失败则报错
        String phone = (String) phoneCode.get("phone");
        String inputCode = (String) phoneCode.get("code");

//        去掉手机号空格，验证手机号有无长度，是否纯数字手机格式
        if (null == phone) {
            return R.fail(ExceptionCodeEnum.PHONE_NOT_TYPED);
        }
        if (!StringUtils.hasLength(phone)) {
            return R.fail(ExceptionCodeEnum.PHONE_NOT_TYPED);
        }

        String sessionCode = (String) session.getAttribute(ConstArgs.SMSCODE_SESSION);
        if (!inputCode.equals(sessionCode)) { // 包装类比较值的大小,不相等，登录不成功
            return R.fail(ExceptionCodeEnum.LOGIN_VERIFY_CODE_ERROR);
        }

        //        读取DB中是否此电话已经注册，未注册插入，已注册：将userid 写入session
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User userInfo = userService.getOne(queryWrapper);
        if (userInfo == null) { // 新手机号，插入用户信息
            userInfo = new User();
            userInfo.setPhone(phone);
            userInfo.setStatus(1);
            userService.save(userInfo);
        }
        session.setAttribute(ConstArgs.USERID_SESSION, userInfo.getId());
        return R.success("登录成功");
    }

    @PostMapping("/sendMsg")
    public R<String> frontSendSMS(HttpServletRequest request, @RequestBody Map phone) {

//        提取手机号并且验证
//        生成验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
//         发送短信，并且报存在session或者缓存中

        request.getSession().setAttribute(ConstArgs.SMSCODE_SESSION, code);
        log.info(phone.toString() + code);

        return R.success("验证码已发送至短信，请注意查收： " + code);
    }

    @PostMapping("/logout")
    public R<String> frontUserLogout(HttpServletRequest request) {
        //清理Session中保存的当前用户的 userid 和电话
        request.getSession().removeAttribute(ConstArgs.USERID_SESSION);

        return R.success("成功退出登录 ");
    }

}

