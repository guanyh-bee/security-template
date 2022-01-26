package com.lmm.task.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmm.task.entity.role.RoleAuthority;
import com.lmm.task.entity.role.RoleAuthorityVO;
import com.lmm.task.entity.role.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface RoleAuthorityMapper  extends BaseMapper<RoleAuthority> {
    IPage<UserRoleVO> getRoleByUser(Page page);
    List<RoleAuthorityVO> getRoleAuthority();

}
