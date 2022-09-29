package com.bing.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.common.ConstArgs;
import com.bing.common.ExceptionCodeEnum;
import com.bing.common.R;
import com.bing.entity.CategoryDO;
import com.bing.entity.DTO.CategoryDTO;
import com.bing.entity.VO.CategoryVO;
import com.bing.service.CategoryService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 菜品及套餐分类(Category)表控制层
 *
 * @author makejava
 * @since 2022-09-26 22:12:42
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    /**
     * 服务对象
     */
//    @Autowired
    @Resource(type = CategoryService.class)
    private CategoryService categoryService;

    /**
     * 分页查询
     *
     * @param pageRequest 分页对象
     * @param category    筛选条件
     * @return 查询结果
     */
    @GetMapping("/page") // Controller 默认接收的是URL 参数
    public R<Page> queryByPage(CategoryVO category) {
        log.info("分页查询分类情况：{}，查询条件：{}", 1, category);
        CategoryDTO categoryDTO = new CategoryDTO();
        MyBeanUtil.copyProperties(category, categoryDTO);

        // 按照分页查询DB，并且将 DO 转为 VO 响应给前端
        Page<CategoryDO> onePage = categoryService.queryByPage(categoryDTO, category.getPage(), category.getPageSize());
        onePage.convert(oneCategory -> {
            CategoryVO categoryVO = new CategoryVO();
            MyBeanUtil.copyProperties(oneCategory, categoryVO);
            return categoryVO;
        });

        return R.success(onePage);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public R<CategoryVO> queryById(@PathVariable("id") Long id) {
        CategoryDTO categoryDTO = categoryService.queryById(id);
        CategoryVO categoryVO = new CategoryVO();
        MyBeanUtil.copyProperties(categoryDTO, categoryVO);
        return R.success(categoryVO);
//        return ResponseEntity.ok(this.categoryService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param category 实体
     * @return 新增结果
     */
    @PostMapping
    public R<String> add(HttpServletRequest request, @RequestBody CategoryVO category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        MyBeanUtil.copyProperties(category, categoryDTO);
        categoryDTO.setUpdate_user((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));

        categoryDTO.setCreate_user((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));
        categoryService.insert(categoryDTO);
        return R.success("分类信息：" + category.getName() + " 添加成功！");
    }

    /**
     * 编辑数据
     *
     * @param category 实体
     * @return 编辑结果
     */
    @PutMapping
    public R<String> edit(HttpServletRequest request, @RequestBody CategoryVO category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        MyBeanUtil.copyProperties(category, categoryDTO);
        categoryDTO.setUpdate_user((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));

        categoryService.update(categoryDTO);
        return R.success("分类信息：" + category.getName() + "修改成功！");
    }

    /**
     * 删除数据
     * 通过 URL 路径传值。未使用
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{ids}")
    public R<String> deleteByPathId(@PathVariable("ids") Long id) {
        boolean result = categoryService.deleteById(id);
        return result ? R.success("分类：" + id + " 删除成功！") : R.fail(ExceptionCodeEnum.DB_ERROR);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public R<String> deleteById(@RequestParam("ids") Long id) {
        boolean result = categoryService.deleteById(id);
        return result ? R.success("分类：" + id + " 删除成功！") : R.fail(ExceptionCodeEnum.DB_ERROR);
    }
}

