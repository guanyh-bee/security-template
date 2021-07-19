package com.example.vue3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vue3.entity.MyUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyUserMapper extends BaseMapper<MyUser> {
    MyUser getUserDetail(String username);
}
