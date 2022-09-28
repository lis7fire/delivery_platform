package com.bing.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bing.common.ConstArgs;
import com.bing.common.ExceptionCodeEnum;
import com.bing.common.R;
import com.bing.entity.CategoryDO;
import com.bing.entity.DTO.CategoryDTO;
import com.bing.entity.VO.CategoryVO;
import com.bing.entity.VO.PageRequestVO;
import com.bing.service.CategoryService;
import com.bing.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
     * @param category    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping("/page")
    public ResponseEntity<Page<CategoryDO>> queryByPage(CategoryDO category, PageRequestVO pageRequest) {
        log.info("分页查询分类情况：{}，查询条件：{}", pageRequest, category);
//        return ResponseEntity.ok(this.categoryService.queryByPage(category, pageRequest));
        return null;
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
    public R<String> add(HttpServletRequest request, CategoryVO category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        MyBeanUtil.copyProperties(category, categoryDTO);
        categoryDTO.setUpdate_user((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));

        categoryDTO.setCreate_user((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));
        categoryService.insert(categoryDTO);
        return R.success("分类信息：" + category.getName() + "添加成功！");
    }

    /**
     * 编辑数据
     *
     * @param category 实体
     * @return 编辑结果
     */
    @PutMapping
    public R<String> edit(HttpServletRequest request, CategoryVO category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        MyBeanUtil.copyProperties(category, categoryDTO);
        categoryDTO.setUpdate_user((Long) request.getSession().getAttribute(ConstArgs.EMPLOYEE_ID_SESSION));

        categoryService.update(categoryDTO);
        return R.success("分类信息：" + category.getName() + "修改成功！");
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public R<String> deleteById(@PathVariable("id") Long id) {
        boolean result = categoryService.deleteById(id);
        return result ? R.success("分类：" + id + "删除成功！") : R.fail(ExceptionCodeEnum.DB_ERROR);
    }
}

