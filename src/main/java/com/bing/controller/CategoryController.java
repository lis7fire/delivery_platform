package com.bing.controller;

import com.bing.entity.CategoryDO;
import com.bing.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜品及套餐分类(Category)表控制层
 *
 * @author makejava
 * @since 2022-09-26 22:12:42
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 分页查询
     *
     * @param category    筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
//    @GetMapping
//    public ResponseEntity<Page<CategoryDO>> queryByPage(CategoryDO category, PageRequest pageRequest) {
//        return ResponseEntity.ok(this.categoryService.queryByPage(category, pageRequest));
//    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<CategoryDO> queryById(@PathVariable("id") Long id) {
        return null;
//        return ResponseEntity.ok(this.categoryService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param category 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<CategoryDO> add(CategoryDO category) {
        return null;
//        return ResponseEntity.ok(this.categoryService.insert(category));
    }

    /**
     * 编辑数据
     *
     * @param category 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<CategoryDO> edit(CategoryDO category) {
        return null;
//        return ResponseEntity.ok(this.categoryService.update(category));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return null;
//        return ResponseEntity.ok(this.categoryService.deleteById(id));
    }

}

