package com.bing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bing.common.R;
import com.bing.controller.VO.EmployeeVO;
import com.bing.entity.EmployeeDO;
import com.bing.service.DTO.EmployeeDTO;
import com.bing.service.EmployeeService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author:  LiBingYan
 * @时间:  2022/9/19
 */
@Slf4j
@RestController
@RequestMapping(value = "/employee/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("login")
    public R employeeLogin(HttpServletRequest request, @RequestBody EmployeeDTO employeeDTO) {

        //1、将页面提交的密码password进行md5加密处理;一般将MD5放在前端
        String password = employeeDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库;
        //3、如果没有查询到则返回登录失败结果
        EmployeeDTO loginResult = employeeService.verifyUsernamePassword(employeeDTO);

        if (loginResult == null) {
            log.error("  查询不到,账号不存在  ");
            return R.fail(404, "用户名或密码错误，登录失败");
        }
        //4、密码比对，如果不一致则返回登录失败结果
        if (!loginResult.getPassword().equals(password)) {
            log.error("  账号存在,密码错误  ");
            return R.fail(405, "用户名或密码错误，登录失败");
        }
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (loginResult.getStatus() == 0) {
            log.error("  密码正确，账户被禁用  ");
            return R.fail(406, "账号已禁用，登录失败");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", loginResult.getEmployeeID());
        //7.将 结果转换成VO响应给前端
        EmployeeVO employeeVO = new EmployeeVO();
        MyBeanUtil.copyProperties(loginResult, employeeVO, "password", "status");
        employeeVO.getEmployeeID();
        log.info(" 登录成功，已经写入 session ：{} ", request.getSession().toString());
        return R.success(employeeVO);
    }
}
