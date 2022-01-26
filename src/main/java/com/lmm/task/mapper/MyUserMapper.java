package com.lmm.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmm.task.entity.BaseInfo;
import com.lmm.task.entity.MyUser;
import com.lmm.task.entity.MyUserBaseInfo;
import com.lmm.task.entity.MyUserBaseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyUserMapper extends BaseMapper<MyUser> {
    MyUser getUserDetail(String username);
    MyUserBaseInfo getUserInfo(Integer id);
    List<BaseInfo> getAllUserByDept(List<Integer> ids);
    List<BaseInfo> getSingleUserByDept(Integer userId);

    List<MyUserBaseVO> searchByNameOrAccount(String key);
}
