package com.bing.controller;

import com.bing.common.R;
import com.bing.controller.VO.EmployeeVO;
import com.bing.service.DTO.EmployeeDTO;
import com.bing.service.EmployeeService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;

/**
 * @author: LiBingYan
 * @时间: 2022/9/19
 */
@Slf4j
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * -------------------------------------------------
     * 员工登录接口
     * @params:
     * @author: LiBingYan
     * @时间: 2022/9/19
     */
    @PostMapping("/login")
    public R<EmployeeVO> employeeLogin(HttpServletRequest request, @RequestBody EmployeeDTO employeeDTO) {

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


        //6.将 结果转换成VO响应给前端
        EmployeeVO employeeVO = new EmployeeVO();
        MyBeanUtil.copyProperties(loginResult, employeeVO, "password", "status");

//        Enumeration<String> a = request.getSession().getAttributeNames();
//        log.info(String.valueOf(a instanceof Collections));
//        System.out.println(a.nextElement());
//        System.out.println(a.hasMoreElements());

        //7、登录成功，将员工id存入Session并返回登录成功结果
        //session 独立于每个连接，只要一个连接不断，session中设置的本连接的 KV 就不变；每个连接的  getAttribute("employeeId_session") 是不同的
        request.getSession().setAttribute("employeeId_session", loginResult.getEmployeeID());
        log.info(" 登录成功，已经写入 session ：{} ", request.getSession().getAttribute("employeeId_session"));

        return R.success(employeeVO);
    }

    /**
     * 员工退出接口
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employeeId_session");
        log.info(" 登出操作， 现有的 session ：{} ", request.getSession().getAttribute("employeeId_session"));
        return R.success("退出成功");
    }

    @GetMapping("/session")
    public R<String> getSession(HttpServletRequest httpServletRequest) {
        return R.success("当前连接 的 employeeId_session 值为 ：" + httpServletRequest.getSession().getAttribute("employeeId_session"));
    }

    /**
     * -------------------------------------------------
     * 所有员工列表，分页查询
     *
     * @params: 第几页，每页几行，查询的员工名
     * @author: LiBingYan
     * @时间: 2022/9/19
     */
    @GetMapping("/page")
    public R<EmployeeVO> page(HttpServletRequest httpServletRequest, int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        return null;
    }

}
