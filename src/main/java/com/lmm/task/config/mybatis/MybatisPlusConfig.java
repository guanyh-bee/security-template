package com.lmm.task.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lmm.task.entity.MyUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisPlusConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        MyUser principal = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.fillStrategy(metaObject, "created", LocalDateTime.now());
        this.fillStrategy(metaObject, "modified", LocalDateTime.now());
        this.fillStrategy(metaObject, "createdBy", principal.getMyUserId());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "modified", LocalDateTime.now());
    }


}
