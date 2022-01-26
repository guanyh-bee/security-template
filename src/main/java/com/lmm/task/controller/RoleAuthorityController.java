package com.lmm.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmm.task.entity.dept.Dept;
import com.lmm.task.entity.MyAuthority;
import com.lmm.task.entity.MyUser;
import com.lmm.task.entity.role.Role;
import com.lmm.task.entity.role.RoleAuthority;
import com.lmm.task.entity.role.RoleAuthorityVO;
import com.lmm.task.entity.role.UserRoleVO;
import com.lmm.task.entity.user.UserRole;
import com.lmm.task.mapper.dept.DeptMapper;
import com.lmm.task.mapper.MyUserMapper;
import com.lmm.task.mapper.role.MyAuthorityMapper;
import com.lmm.task.mapper.role.RoleAuthorityMapper;
import com.lmm.task.mapper.role.RoleMapper;
import com.lmm.task.mapper.user.UserRoleMapper;
import com.lmm.task.utils.SecurityUtils;
import com.lmm.task.utils.errorCode.CommonResult;
import com.lmm.task.utils.errorCode.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleAuthorityController {

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private MyAuthorityMapper myAuthorityMapper;
    @Autowired
    private MyUserMapper myUserMapper;

    @GetMapping("/byUser/{currentPage}/{pageSize}")
    public CommonResult<IPage<UserRoleVO>> getUserRoleList(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        IPage<UserRoleVO> pageRoleByUser = roleAuthorityMapper.getRoleByUser(new Page<>().setSize(pageSize).setCurrent(currentPage));
        List<UserRoleVO> roleByUser = pageRoleByUser.getRecords();
        List<UserRoleVO> userRoleVOS = roleByUser.stream().map(userRoleVO -> {
            Dept dept = deptMapper.selectById(userRoleVO.getDeptId());
            String wholeDeptName = SecurityUtils.getWholeDeptName(dept.getDeptId());
            userRoleVO.setDeptName(wholeDeptName);
            return userRoleVO;

        }).collect(Collectors.toList());
        pageRoleByUser.setRecords(userRoleVOS);

        return new CommonResult<>(ErrorCode.SUCCESS, pageRoleByUser);

    }


    @GetMapping("/roles")
    public CommonResult<List<Role>> getRoles() {
        List<Role> roles = roleMapper.selectList(null);
        return new CommonResult<>(ErrorCode.SUCCESS, roles);
    }

    //    Integer userId,@RequestBody List<String> roleId
    @PostMapping("/updateRole")
    @Transactional
    public CommonResult updateRole(@RequestBody JSONObject object) {
        Integer userId = object.getInteger("userId");
        List<Integer> newRoleId = object.getJSONArray("roleId").toJavaList(Integer.class);
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        userRoleMapper.delete(userRoleQueryWrapper);

        for (Integer integer : newRoleId) {
            UserRole userRole = new UserRole().setUserId(userId).setRoleId(integer);
            userRoleMapper.insert(userRole);
        }


        return new CommonResult(ErrorCode.SUCCESS);
    }

    //    @GetMapping("/getUserByRole/{roleId}")
//    public CommonResult<RoleUserVO> getUserByRole(@PathVariable Integer roleId){
//        if (roleId == null){
//            return new CommonResult<>(ErrorCode.COMMON_TYPE);
//        }
//        RoleUserVO userByRole = roleUserMapper.getUserByRole(roleId);
//
//        return new CommonResult<>(ErrorCode.SUCCESS,userByRole);
//    }
    @GetMapping("/authorities/{current}/{size}")
    public CommonResult<IPage<MyAuthority>> getAuthority(@PathVariable Integer size,@PathVariable Integer current) {
        Page<MyAuthority> myAuthorityPage = myAuthorityMapper.selectPage(new Page<>(current, size), null);
        return new CommonResult<>(ErrorCode.SUCCESS, myAuthorityPage);
    }

    @GetMapping("/allAuthorities")
    public CommonResult<List<MyAuthority>> getAllAuthority() {
        List<MyAuthority> myAuthorities = myAuthorityMapper.selectList(null);
        return new CommonResult<>(ErrorCode.SUCCESS, myAuthorities);
    }

    @PostMapping("/updateAuthority/{id}/{enable}")
    public CommonResult<Void> updateAuthority(@PathVariable Integer id,@PathVariable boolean enable){

        myAuthorityMapper.updateById(new MyAuthority().setId(id).setEnable(enable));

        return new CommonResult<>(ErrorCode.SUCCESS);

    }


    @GetMapping("/getAuthority")
    public CommonResult<List<RoleAuthorityVO>> getAuthority(){
        List<RoleAuthorityVO> roleAuthority = roleAuthorityMapper.getRoleAuthority();
        return new CommonResult(ErrorCode.SUCCESS,roleAuthority);
    }

    @PostMapping("/enable/{userId}/{enabled}")
    public CommonResult<Void> enableAccount(@PathVariable Integer userId,@PathVariable boolean enabled){
        myUserMapper.updateById(new MyUser().setMyUserId(userId).setEnabled(enabled).setCredentialsNonExpired(true).setAccountNonExpired(true).setAccountNonLocked(true));
        return new CommonResult<>(ErrorCode.SUCCESS);
    }

    @PostMapping("/updateRoleAuthority")
    @Transactional
    public CommonResult<Void> updateRoleAuthority(@RequestBody JSONObject object){
        Integer roleId = object.getInteger("roleId");
        List<Integer> authorities = object.getJSONArray("authorityIds").toJavaList(Integer.class);
        QueryWrapper<RoleAuthority> roleAuthorityQueryWrapper = new QueryWrapper<>();
        roleAuthorityQueryWrapper.eq("role_id",roleId);
        roleAuthorityMapper.delete(roleAuthorityQueryWrapper);
        authorities.forEach(a->{
            roleAuthorityMapper.insert(new RoleAuthority().setRoleId(roleId).setAuthorityId(a));
        });
        return new CommonResult<>(ErrorCode.SUCCESS);
    }

    @GetMapping("/getDownRole")
    public CommonResult<List<Role>> getDownRole(){
        Optional<Role> min = SecurityUtils.getRole().stream().min(Comparator.comparingInt(Role::getLevel));
        Role role = min.get();
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.ge("level",role.getLevel());
        List<Role> roles = roleMapper.selectList(roleQueryWrapper);


        return new CommonResult<>(ErrorCode.SUCCESS,roles);
    }


}
