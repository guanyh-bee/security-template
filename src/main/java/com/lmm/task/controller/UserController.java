package com.lmm.task.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.task.entity.*;
import com.lmm.task.entity.dept.Dept;
import com.lmm.task.entity.dept.DeptPrincipal;
import com.lmm.task.entity.role.Role;
import com.lmm.task.entity.user.UserRole;
import com.lmm.task.mapper.BaseInfoMapper;
import com.lmm.task.mapper.dept.DeptMapper;
import com.lmm.task.mapper.dept.DeptPrincipalMapper;
import com.lmm.task.mapper.MyUserMapper;
import com.lmm.task.mapper.role.RoleMapper;
import com.lmm.task.mapper.user.UserRoleMapper;
import com.lmm.task.utils.CommonUtils;
import com.lmm.task.utils.SecurityUtils;
import com.lmm.task.utils.errorCode.CommonResult;
import com.lmm.task.utils.errorCode.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MyUserMapper myUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private BaseInfoMapper baseInfoMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptPrincipalMapper deptPrincipalMapper;

    @Autowired
    private  DeptMapper deptMapper;
    @PostMapping("/getUserByDeptId")
    public CommonResult<List<BaseInfo>> getAllUser(@RequestBody List<Integer> ids){
        QueryWrapper<DeptPrincipal> deptPrincipalQueryWrapper = new QueryWrapper<>();
        deptPrincipalQueryWrapper.eq("user_id", SecurityUtils.getUserId());
        List<DeptPrincipal> deptPrincipals = deptPrincipalMapper.selectList(deptPrincipalQueryWrapper);
        if (deptPrincipals.size()<1 && !SecurityUtils.isAdmin(SecurityUtils.getRole())){
            List<BaseInfo> singleUserByDept = myUserMapper.getSingleUserByDept(SecurityUtils.getUserId());
            return new CommonResult<>(ErrorCode.SUCCESS,singleUserByDept);

        }
        if (ids.size()>0){
            ids = getDeptIds(ids.get(0));
        }else {

            if (SecurityUtils.isAdmin(SecurityUtils.getRole())){

            }else {
                ids = deptPrincipals.stream().map(deptPrincipal -> deptPrincipal.getDeptId()).collect(Collectors.toList());

            }
        }

        List<BaseInfo> allUserByDept = myUserMapper.getAllUserByDept(ids);
        return new CommonResult<>(ErrorCode.SUCCESS,allUserByDept);
    }

    public List<Integer> getDeptIds(Integer id){
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
        deptQueryWrapper.eq("dept_parent_id", id);
        List<Dept> depts = deptMapper.selectList(deptQueryWrapper);
        if (depts.size()>0){
            for (Dept dept : depts) {
                List<Integer> deptIds = getDeptIds(dept.getDeptId());
                ids.addAll(deptIds);
            }
        }
        return  ids;
    }

//    @PostMapping("/getUserPage")
//    public Page<MyUser> getUserPage(Integer page){
//        Page<MyUser> myUserPage = myUserMapper.selectPage(new Page<>(1, 10), null);
//        return myUserPage;
//    }

    @PostMapping("/addUser")
    @Transactional
    public CommonResult addUser(@RequestBody JSONObject object){

        String username = object.getString("username");
        String password = passwordEncoder.encode("123456");
        MyUser myUser = new MyUser();
        myUser.setPassword(password).setUsername(username);
        myUser.setEnabled(true).setAccountNonExpired(true).setAccountNonLocked(true).setCredentialsNonExpired(true);
        myUserMapper.insert(myUser);

        Integer myUserId = myUser.getMyUserId();
        String realName = object.getString("realName");
        boolean isHost = object.getBoolean("isHost");
        boolean gender = object.getBoolean("gender");
        String phone = object.getString("phone");
        String addr = object.getString("addr");
        String relation = object.getString("relation");
        JSONArray deptIds = object.getJSONArray("dept");
        Integer deptId = deptIds.getInteger(deptIds.size() - 1);

        BaseInfo baseInfo = new BaseInfo();

        baseInfo.setAddr(addr)
                .setMyUserId(myUserId)
                .setDeptId(deptId)
                .setGender(gender?1:0)
                .setHost(isHost)
                .setRealName(realName)
                .setPhone(phone)
                .setRelation(relation);

        baseInfoMapper.insert(baseInfo);
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("level",6);
        Role role = roleMapper.selectOne(roleQueryWrapper);
        userRoleMapper.insert(new UserRole().setUserId(myUserId).setRoleId(role.getId()));

        return new CommonResult(ErrorCode.SUCCESS);
    }

    @RequestMapping("/info")
    public CommonResult info(){
        MyUser principal = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUserBaseInfo userInfo = myUserMapper.getUserInfo(principal.getMyUserId());
        userInfo.setListAuthorities(principal.getListAuthorities());
        return new CommonResult(ErrorCode.SUCCESS,userInfo);
    }

    @GetMapping("/search/{deptId}/{key}")
    public CommonResult<List<MyUserBaseVO>> search(@PathVariable String key, @PathVariable Integer deptId){
        List<MyUserBaseVO> baseInfos = new ArrayList<>();
        if (StringUtils.hasLength(key)) {
            baseInfos = myUserMapper.searchByNameOrAccount(key);
            QueryWrapper<DeptPrincipal> deptPrincipalQueryWrapper = new QueryWrapper<>();
            deptPrincipalQueryWrapper.eq("dept_id", deptId);
            List<DeptPrincipal> deptPrincipals = deptPrincipalMapper.selectList(deptPrincipalQueryWrapper);

            baseInfos =  baseInfos.stream().map(myUserBaseVO -> {
                myUserBaseVO.setDisabled(false);
                for (DeptPrincipal deptPrincipal : deptPrincipals) {
                    if (deptPrincipal.getUserId() == myUserBaseVO.getUserId()){
                        myUserBaseVO.setDisabled(true);
                        break;
                    }
                }
                return myUserBaseVO;
            }).collect(Collectors.toList());


        }

        return new CommonResult<>(ErrorCode.SUCCESS,baseInfos);
    }


    @PostMapping("/getNameById")
    public CommonResult<List<String>> getNameByIds(@RequestBody JSONObject object){
        List<Integer> userIds = object.getJSONArray("userIds").toJavaList(Integer.class);
        List<String> strings = CommonUtils.getNameById(userIds);

        return new CommonResult<>(ErrorCode.SUCCESS,strings);
    }

    @PostMapping("/getUserByRole")
    public CommonResult<List<BaseInfo>> getUserByRole(@RequestBody JSONObject object){
        List<Integer> integers = object.getJSONArray("roleIds").toJavaList(Integer.class);
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.in("role_id",integers);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
        List<Integer> collect = userRoles.stream().map(userRole -> userRole.getUserId()).collect(Collectors.toList());
        QueryWrapper<BaseInfo> baseInfoQueryWrapper = new QueryWrapper<>();
        baseInfoQueryWrapper.in("my_user_id",collect);
        List<BaseInfo> baseInfos = baseInfoMapper.selectList(baseInfoQueryWrapper);
        return new CommonResult<>(ErrorCode.SUCCESS,baseInfos);
    }

}
