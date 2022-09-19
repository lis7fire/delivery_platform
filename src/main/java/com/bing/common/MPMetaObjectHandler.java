package com.bing.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * -------------------------------------------------
 *
 * @功能: MP功能，实现 实体类中 @TableField(fill = FieldFill.INSERT) 自动填充功能
 * @params:
 * @作者: LiBingYan
 * @时间: 2022/9/16
 * --------------------------------------------------
 */
@Slf4j
@Component
public class MPMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
//        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
