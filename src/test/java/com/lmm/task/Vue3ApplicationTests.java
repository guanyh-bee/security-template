package com.lmm.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmm.task.config.security.MyAccessDecisionManager;
import com.lmm.task.entity.*;
import com.lmm.task.entity.dept.Dept;
import com.lmm.task.entity.dept.DeptTree;
import com.lmm.task.entity.dept.Village;
import com.lmm.task.entity.role.RoleAuthorityVO;
import com.lmm.task.entity.role.UserRoleVO;
import com.lmm.task.mapper.dept.DeptMapper;
import com.lmm.task.mapper.dept.VillageMapper;
import com.lmm.task.mapper.role.MyAuthorityMapper;
import com.lmm.task.mapper.MyUserMapper;
import com.lmm.task.mapper.role.RoleAuthorityMapper;
import com.lmm.task.mapper.role.RoleUserMapper;
import com.lmm.task.mapper.task.TaskItemMapper;
import com.lmm.task.utils.JWTutils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class Vue3ApplicationTests {
    @Resource
    MyUserMapper myUserMapper;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    DeptMapper deptMapper;

    @Resource
    RoleUserMapper roleUserMapper;

    @Resource
    RoleAuthorityMapper roleAuthorityMapper;

    @Resource
    MyAccessDecisionManager myAccessDecisionManager;

    @Resource
    MyAuthorityMapper mapper;
    @Resource
    TaskItemMapper taskItemMapper;

    @Resource
    VillageMapper villageMapper;
    @Test
    void contextLoads() {
        System.out.println(myUserMapper.getUserDetail("管运好"));

    }
    @Test
    void test2(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(3);
        List<BaseInfo> allUser = myUserMapper.getAllUserByDept(null);
        System.out.println(allUser);
    }

    @Test
    void contextLoads1() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","管运好");
        map.put("age","28");
        String token = JWTutils.generateToken(map);
        System.out.println(token);

        System.out.println(JWTutils.verifyJwt(token).get("name"));

    }

    @Test
    void test3(){
        System.out.println(mapper.selectList(null));
    }

    @Test
    void test4(){
        System.out.println(myAccessDecisionManager);
    }

    @Test
    void test5(){
        String data = "{\"1_attr1\":false,\"1_attr2\":0,\"6_attr1\":false,\"6_attr2\":0,\"8_attr1\":false,\"8_attr2\":0}";
        JSONObject object = JSONObject.parseObject(data);
        Map<String,String> map = new HashMap<>();
        Map<String, Object> innerMap = object.getInnerMap();

        ArrayList<Map.Entry<String, Object>> entries = new ArrayList<>(innerMap.entrySet());
        Collections.sort(entries, Comparator.comparing(Map.Entry::getKey));
        System.out.println(entries);


    }

    @Test
    void test6(){
        MyUser userDetail = myUserMapper.getUserDetail("管运好");
        System.out.println(userDetail);


    }

    @Test
    void test7(){
        MyUserBaseInfo userInfo = myUserMapper.getUserInfo(1);
        System.out.println(userInfo);
    }

    @Test
    void test8(){
        IPage<UserRoleVO> roleByUser = roleAuthorityMapper.getRoleByUser(new Page(1,5));
        System.out.println(roleByUser.getTotal());
    }

    @Test
    void test9(){
        String wholeDeptName = getWholeDeptName(21);
        System.out.println("wholeDeptName = " + wholeDeptName);
    }

    @Test
    void test10(){
        List<RoleAuthorityVO> roleAuthority = roleAuthorityMapper.getRoleAuthority();
        System.out.println("roleAuthority = " + roleAuthority);
    }

    @Test
    void test11(){
        List<Integer> integers = new ArrayList<>();

        integers.add(22);
        List<DeptTree> needTree = getNeedTree(integers, getTreeById(deptMapper.selectById(1)));

        System.out.println("needTree = " + needTree);
    }

    @Test
    void test12(){
        villageMapper.insert(new Village().setVillageOrder("88888").setDeptId(1).setDescription("测试"));
    }

    @Test
    void test13(){
        List<RoleAuthorityVO> roleAuthority = roleAuthorityMapper.getRoleAuthority();
        System.out.println("roleAuthority = " + roleAuthority);
    }


    public String getWholeDeptName(Integer deptId){

        Dept dept = deptMapper.selectById(deptId);
        StringBuilder wholeDeptName = new StringBuilder(dept.getDeptName());
        Dept dept1 = deptMapper.selectById(dept.getDeptParentId());

        if (dept1 != null) {
            wholeDeptName.insert(0,getWholeDeptName(dept1.getDeptId())+"/");
        }

        return wholeDeptName.toString();
    }




    public List<DeptTree> getNeedTree(List<Integer> needIds, DeptTree deptTree){
        List<DeptTree> deptTrees = new ArrayList<>();
        boolean flag = false;
        for (Integer needId : needIds) {
            if (needId == deptTree.getId()){
                deptTrees.add(deptTree);
                flag = true;
            }
        }
        if (flag){
            return  deptTrees;
        }else {
            if (deptTree.getChildren() != null && deptTree.getChildren().size()>0){
                for (DeptTree child : deptTree.getChildren()) {
                    if (getNeedTree(needIds,child) != null){
                        deptTrees.addAll(getNeedTree(needIds,child));
                    }

                }
                return deptTrees;
            }else {
                return null;
            }
        }

    }

    public DeptTree getTreeById(Dept dept) {
        DeptTree deptTree = new DeptTree();
        deptTree.setId(dept.getDeptId());
        deptTree.setLabel(dept.getDeptName());
        deptTree.setDeptLevel(dept.getDeptLevel());
        deptTree.setDisabled(dept.getDeptLevel() == 0 || dept.getDeptLevel() == 1 ||dept.getDeptLevel() == 2);
        QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
        deptQueryWrapper.eq("dept_parent_id", dept.getDeptId());
        List<Dept> depts = deptMapper.selectList(deptQueryWrapper);
        deptQueryWrapper.clear();
        List<DeptTree> deptTrees;
        if (depts.size() > 0) {
            deptTrees = depts.stream().map(dept1 -> {
                DeptTree treeById = getTreeById(dept1);
                return treeById;
            }).collect(Collectors.toList());
            deptTree.setChildren(deptTrees);
        } else {
            deptTree.setChildren(null);
        }

        return deptTree;
    }

}
