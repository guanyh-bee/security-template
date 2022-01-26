package com.lmm.task.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.task.entity.BaseInfo;
import com.lmm.task.mapper.BaseInfoMapper;
import com.lmm.task.mapper.user.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CommonUtils {

    public static   BaseInfoMapper baseInfoMapper;


    @Autowired
    public CommonUtils(BaseInfoMapper baseInfoMapper){
        this.baseInfoMapper = baseInfoMapper;
    }

    public static List<String> getNameById(List<Integer> ids){
        List<String> strings = ids.stream().map(integer -> {
            QueryWrapper<BaseInfo> baseInfoQueryWrapper = new QueryWrapper<>();
            baseInfoQueryWrapper.eq("my_user_id", integer);
            BaseInfo baseInfo = baseInfoMapper.selectOne(baseInfoQueryWrapper);
            return baseInfo.getRealName();

        }).collect(Collectors.toList());

        return strings;
    }

    public static List<BaseInfo> getNameAndIdById(List<Integer> ids){
        List<BaseInfo> strings = ids.stream().map(integer -> {
            QueryWrapper<BaseInfo> baseInfoQueryWrapper = new QueryWrapper<>();
            baseInfoQueryWrapper.eq("my_user_id", integer);
            BaseInfo baseInfo = baseInfoMapper.selectOne(baseInfoQueryWrapper);
            return baseInfo;

        }).collect(Collectors.toList());

        return strings;
    }
}
