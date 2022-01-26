package com.lmm.task.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmm.task.entity.role.Role;
import com.lmm.task.entity.role.RoleUserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleUserMapper extends BaseMapper<RoleUserVO> {
    RoleUserVO getUserByRole(Integer roleId);
}
