package com.bing.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * -------------------------------------------------
 *
 * @功能: MP功能，实现 实体类中 @TableField(fill = FieldFill.INSERT) 字段自动填充功能
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
        log.info("MP公共字段自动填充：start [插入操作，自动填充] fill ....");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        //客户端每次发送的请求都对应一个线程。
        Long threadId = Thread.currentThread().getId();
        log.info("对应线程id：{}", threadId);

//        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("MP公共字段自动填充：start [更新操作，自动填充] fill ....");
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
