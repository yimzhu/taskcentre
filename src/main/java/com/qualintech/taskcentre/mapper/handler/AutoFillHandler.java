package com.qualintech.taskcentre.mapper.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author yimzhu
 */
@Component
@Slf4j
public class AutoFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert field....");
        boolean insertTime = metaObject.hasSetter("insert_time");
        log.info("insertTime:"+insertTime);
        if(insertTime){
            this.strictInsertFill(metaObject, "insert_time", ZonedDateTime.class, ZonedDateTime.now());
        }

//        this.setFieldValByName("insert_time", LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateTime", ZonedDateTime.now(),metaObject);
    }
}