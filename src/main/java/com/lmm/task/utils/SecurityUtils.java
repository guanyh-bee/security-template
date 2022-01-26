package com.lmm.task.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.task.entity.BaseInfo;
import com.lmm.task.entity.dept.Dept;
import com.lmm.task.entity.MyUser;
import com.lmm.task.entity.role.Role;
import com.lmm.task.entity.user.UserRole;
import com.lmm.task.mapper.BaseInfoMapper;
import com.lmm.task.mapper.dept.DeptMapper;
import com.lmm.task.mapper.role.RoleMapper;
import com.lmm.task.mapper.user.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public  class  SecurityUtils {


    public static UserRoleMapper userRoleMapper;
    public static RoleMapper roleMapper;
    public static BaseInfoMapper baseInfoMapper;
    public static DeptMapper deptMapper;
    @Autowired
    public SecurityUtils(UserRoleMapper userRoleMapper,RoleMapper roleMapper,BaseInfoMapper baseInfoMapper,DeptMapper deptMapper){
        SecurityUtils.userRoleMapper = userRoleMapper;
        SecurityUtils.roleMapper = roleMapper;
        SecurityUtils.baseInfoMapper = baseInfoMapper;
        SecurityUtils.deptMapper = deptMapper;
    }
    public static Integer getUserId(){
        MyUser principal = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getMyUserId();
    }

    public static MyUser getUser(){

        MyUser principal = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }

    public static List<Role> getRole(){
        Integer userId = getUserId();
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",userId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
        List<Integer> collect = userRoles.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());

        List<Role> roles = collect.stream().map(integer -> roleMapper.selectById(integer)).collect(Collectors.toList());
        return roles;
    }

    public static boolean isAdmin(List<Role> roles){
        boolean flag = false;
        for (Role role : roles) {
            if (role.getLevel() == 1){
                flag = true;
                break;
            }

        }
        return flag;
    }

    public static Dept getDept() {
        QueryWrapper<BaseInfo> baseInfoQueryWrapper = new QueryWrapper<>();
        baseInfoQueryWrapper.eq("my_user_id",getUserId());
        BaseInfo baseInfo = baseInfoMapper.selectOne(baseInfoQueryWrapper);

        Dept dept = deptMapper.selectById(baseInfo.getDeptId());

        return dept;

    }

    public static String getWholeDeptName(Integer deptId) {

        Dept dept = deptMapper.selectById(deptId);
        StringBuilder wholeDeptName = new StringBuilder(dept.getDeptName());
        Dept dept1 = deptMapper.selectById(dept.getDeptParentId());

        if (dept1 != null) {
            wholeDeptName.insert(0, getWholeDeptName(dept1.getDeptId()) + "/");
        }

        return wholeDeptName.toString();
    }
}
