package com.bing.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.common.R;
import com.bing.controller.VO.EmployeeVO;
import com.bing.entity.EmployeeDO;
import com.bing.service.DTO.EmployeeDTO;
import com.bing.service.EmployeeService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
     *
     * @return
     * @params:
     * @author: LiBingYan
     * @时间: 2022/9/19
     */
    @PostMapping("/login")
    public R<EmployeeVO> employeeLogin(HttpServletRequest request, @RequestBody EmployeeDTO employeeDTO) {

        // 已经登录，通知前端直接跳转，不再查询 DB
        if (request.getSession().getAttribute("employeeId_session") != null) {
            return R.fail(201, "已经登录，前端直接跳转页面");
        }
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
    public R<String> getSession(HttpServletRequest httpServletRequest, @RequestBody Map<String, Object> test) {
        log.error("通过map接收的请求体：{ } 其中的values：{ }", test, test.values());
        return R.success("当前连接 的 employeeId_session 值为 ：" + httpServletRequest.getSession().getAttribute("employeeId_session"));
    }

    /**
     * -------------------------------------------------
     * 所有员工列表，分页查询
     *
     * @return
     * @params: 第几页，每页几行，查询的员工名
     * @author: LiBingYan
     * @时间: 2022/9/19
     */
    @GetMapping("/page") //  参数名不匹配异常类型： IllegalStateException
    public R<IPage> page(HttpServletRequest httpServletRequest,
                         @RequestParam(value = "page") Integer currentPage, Integer pageSize, String name) {
        // 调用 service 查询一页
        log.info("所有员工分页查询，page = {},pageSize = {},name = {}", currentPage, pageSize, name);
        Page<EmployeeDO> onePage = employeeService.getByPageByName(currentPage, pageSize, name);

        // 调用工具类，将 page_DO 转换成 page_VO 以展示给前端 ，转换是直接在调用的对象 onePage 上操作的。
        onePage.convert(resultOne -> {
            //resultOne 是原page 中的一条记录，VO 为转换类型后的一条记录
            EmployeeVO vo = new EmployeeVO();
            MyBeanUtil.copyProperties(resultOne, vo);
            return vo;
        });

        log.info(onePage.getRecords().toString());

        return R.success(onePage);
    }

    // 查询-员工详细信息
    @GetMapping(value = "/{employeeID}")
    public R<EmployeeVO> getOneDetil(@PathVariable Long employeeID) {
        log.info("查询-单个员工详细信息,传入ID:{}", employeeID);
        EmployeeDO employeeDO = employeeService.getById(employeeID);
        if (employeeDO == null) {
            return R.fail(404, "没有查询到对应员工信息");
        }
        EmployeeVO employeeVO = new EmployeeVO();
        MyBeanUtil.copyProperties(employeeDO, employeeVO);
        return R.success(employeeVO);
    }


    // 新增-员工
    @PostMapping
    public R<String> addEmployee(HttpServletRequest request, @RequestBody EmployeeVO newEmployeeVO) {
        log.info("新增员工,传入信息:{}", newEmployeeVO);

        EmployeeDTO employeeDTO = vo2DTO(request, newEmployeeVO);
        employeeDTO.setCreate_user(employeeDTO.getUpdate_user());

        boolean result = employeeService.save(employeeDTO);

        return result ? R.success("新增员工成功！") : R.fail(405, "添加失败，一般不会运行到这里，因为失败了一定会有异常");
    }

    // 修改-员工详细信息  //修改-账号禁用状态  //删除-员工
    @PutMapping
    public R<String> editEmployee(HttpServletRequest request, @RequestBody EmployeeVO newEmployeeVO) {
        log.info("修改员工资料,传入信息:{}", newEmployeeVO);

        EmployeeDTO employeeDTO = vo2DTO(request, newEmployeeVO);

        employeeService.update(employeeDTO);
        return R.success("员工信息修改成功！");
    }

    //  VO 转为 DTO ,并 向 DTO 加入 更新操作人 ID
    private EmployeeDTO vo2DTO(HttpServletRequest request, EmployeeVO newEmployeeVO) {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        MyBeanUtil.copyProperties(newEmployeeVO, employeeDTO);

        // 获取当前登录用户的 ID 设置其为操作人
        Long loginEmployeeId = (Long) request.getSession().getAttribute("employeeId_session");
        employeeDTO.setUpdate_user(loginEmployeeId);

        return employeeDTO;
    }

}
