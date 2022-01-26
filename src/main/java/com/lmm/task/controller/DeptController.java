package com.lmm.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.task.entity.BaseInfo;
import com.lmm.task.entity.dept.*;
import com.lmm.task.mapper.dept.*;
import com.lmm.task.mapper.MyUserMapper;
import com.lmm.task.utils.CommonUtils;
import com.lmm.task.utils.SecurityUtils;
import com.lmm.task.utils.errorCode.CommonResult;
import com.lmm.task.utils.errorCode.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private MyUserMapper myUserMapper;
    @Autowired
    private DeptPrincipalMapper deptPrincipalMapper;
    @Autowired
    private TownshipMapper townshipMapper;
    @Autowired
    private VillageMapper villageMapper;
    @Autowired
    private SocietyMapper societyMapper;
    @Autowired
    private HouseholdMapper householdMapper;

    @GetMapping
    public CommonResult<List<Dept>> getDept() {
        List<Dept> depts = deptMapper.selectList(null);
        return new CommonResult<>(ErrorCode.SUCCESS, depts);
    }

    @GetMapping("/deleteDept/{id}")
    @Transactional
    public CommonResult deleteDept(@PathVariable Integer id) {

        Dept dept = deptMapper.selectById(id);
        if (dept.getDeptLevel() == 0) {
            return new CommonResult(ErrorCode.CAN_NOT_DELETE_ROOT);
        }

        List<Integer> deptIds = getDeptIds(id);
        List<BaseInfo> allUserByDept = myUserMapper.getAllUserByDept(deptIds);
        if (allUserByDept.size() > 0) {
            return new CommonResult(ErrorCode.DEPT_HAVE_PERSON);
        }
        for (Integer deptId : deptIds) {
            Dept dept1 = deptMapper.selectById(deptId);
            switch (dept1.getDeptLevel()){
                case 0:
                    QueryWrapper<Township> townshipQueryWrapper = new QueryWrapper<>();
                    townshipQueryWrapper.eq("dept_id",dept1.getDeptId());
                    townshipMapper.delete(townshipQueryWrapper);
                    break;
                case 1:
                    QueryWrapper<Village> villageQueryWrapper = new QueryWrapper<>();
                    villageQueryWrapper.eq("dept_id",dept1.getDeptId());
                    villageMapper.delete(villageQueryWrapper);
                    break;
                case 2:
                    QueryWrapper<Society> societyQueryWrapper = new QueryWrapper<>();
                    societyQueryWrapper.eq("dept_id",dept1.getDeptId());
                    societyMapper.delete(societyQueryWrapper);
                    break;
                case 3:
                    QueryWrapper<Household> householdQueryWrapper = new QueryWrapper<>();
                    householdQueryWrapper.eq("dept_id",dept1.getDeptId());
                    householdMapper.delete(householdQueryWrapper);
                    break;
            }

            deptMapper.deleteById(deptId);



        }
        return new CommonResult(ErrorCode.SUCCESS);
    }

    @GetMapping("/getTree")
    public CommonResult<List<DeptTree>> getTree() {
        boolean admin = SecurityUtils.isAdmin(SecurityUtils.getRole());
        QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
        deptQueryWrapper.eq("dept_level", 0);
        Dept dept = deptMapper.selectOne(deptQueryWrapper);
        List<DeptTree> deptTrees = new ArrayList<>();
        if (admin) {
            DeptTree treeById = getTreeById(dept);
            deptTrees.add(treeById);
            return new CommonResult<>(ErrorCode.SUCCESS, deptTrees);
        } else {
            QueryWrapper<DeptPrincipal> deptPrincipalQueryWrapper = new QueryWrapper<>();
            deptPrincipalQueryWrapper.eq("user_id", SecurityUtils.getUserId());
            List<DeptPrincipal> deptPrincipals = deptPrincipalMapper.selectList(deptPrincipalQueryWrapper);
            if (deptPrincipals.size() < 1) {
                DeptTree treeById = getTreeById(SecurityUtils.getDept());
                treeById.setCanEdit(0);
                deptTrees.add(treeById);
                return new CommonResult<>(ErrorCode.SUCCESS, deptTrees);
            }

            List<Integer> deptIds = deptPrincipals.stream().map(deptPrincipal -> deptPrincipal.getDeptId()).collect(Collectors.toList());

            return new CommonResult<>(ErrorCode.SUCCESS, getNeedTree(deptIds, getTreeById(dept)));

        }

    }

    @PostMapping("/addDept")
    @Transactional
    public CommonResult addDept(@RequestBody JSONObject object) {
        System.out.println("object = " + object);
        Integer deptParentId = object.getInteger("deptParentId");
        String deptName = object.getString("deptName");
        String type = object.getString("type");
        Dept dept1 = deptMapper.selectById(deptParentId);
        if (dept1.getDeptLevel() >= 3) {
            return new CommonResult(ErrorCode.DEPT_LEVEL);
        }
        QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
        deptQueryWrapper.eq("dept_name", deptName);
        List<Dept> depts = deptMapper.selectList(deptQueryWrapper);
        if (depts.size() > 0) {
            return new CommonResult(ErrorCode.DEPT_DUPLICATE);
        }
        Dept dept = new Dept();
        dept.setDeptName(deptName);
        dept.setDeptLevel(dept1.getDeptLevel() + 1);
        dept.setDeptParentId(deptParentId);
        deptMapper.insert(dept);

        switch (type){
            case "village":
                Village village = new Village();
                village.setDeptId(dept.getDeptId());
                String villageOrder = object.getString("villageOrder");
                village.setVillageOrder(villageOrder);
                village.setDescription(object.getString("description"));
                System.out.println(village);
                villageMapper.insert(village);
                break;
            case "society":
                Society society = new Society();
                society.setDeptId(dept.getDeptId());
                society.setSocietyOrder(object.getString("societyOrder"));
                society.setDescription(object.getString("description"));
                societyMapper.insert(society);
                break;
            case "household":
                Household household = new Household();
                household.setDeptId(dept.getDeptId());
                household.setWuBao(object.getBoolean("wuBao"));
                household.setTakeInSource(object.getString("takeInSource"));
                Boolean tuoPinBuWenDing = object.getBoolean("tuoPinBuWenDing");
                household.setTuoPinBuWenDing(tuoPinBuWenDing);
                if (tuoPinBuWenDing){
                    household.setTuoPinBuWenDingType(object.getString("tuoPinBuWenDingType"));
                }else {
                    household.setTuoPinBuWenDingType(null);
                }
                household.setLiDangJianKa(object.getBoolean("liDangJianKa"));
                household.setHouseholdOrder(object.getString("householdOrder"));
                household.setHouseholdAddr(object.getString("householdAddr"));
                householdMapper.insert(household);
        }

        return new CommonResult(ErrorCode.SUCCESS);
    }

    @GetMapping("/updateDept/{deptId}/{newParentId}/{type}")
    public CommonResult updateDept(@PathVariable Integer deptId, @PathVariable Integer newParentId, @PathVariable Integer type) {
        Dept dept = new Dept();

        if (type == 1) {
            dept.setDeptId(deptId).setDeptParentId(newParentId);
            deptMapper.updateById(dept);
        } else if (type == 2) {
            Dept dept1 = deptMapper.selectById(deptId);
            Dept dept2 = deptMapper.selectById(newParentId);
            if (dept1.getDeptParentId() == dept2.getDeptParentId()) {
                return new CommonResult(ErrorCode.COMMON_TYPE);
            } else {
                dept.setDeptId(deptId).setDeptParentId(dept2.getDeptParentId());
                deptMapper.updateById(dept);
            }
        }
        return new CommonResult(ErrorCode.SUCCESS);
    }

    @PostMapping("/updateNameAndInfo")
    @Transactional
    public CommonResult<Void> updateName(@RequestBody JSONObject object) {
        System.out.println("object = " + object);
        String type = object.getString("type");
        if (object.getString("deptName").isEmpty() && object.getInteger("deptId") == null) {
            return new CommonResult<>(ErrorCode.COMMON_TYPE);
        } else {
            deptMapper.updateById(new Dept().setDeptId(object.getInteger("deptId")).setDeptName(object.getString("deptName")));

        }
        switch (type){
            case "village":
                Village villageInfo = object.getObject("villageInfoSub", Village.class);
                villageMapper.updateById(villageInfo);
                break;
            case "township":
                Township townshipInfo = object.getObject("townshipInfoSub", Township.class);
                townshipMapper.updateById(townshipInfo);
                break;
            case "society":
                Society societyInfo = object.getObject("societyInfoSub", Society.class);
                societyMapper.updateById(societyInfo);
                break;
            case "household":
                Household householdInfo = object.getObject("householdInfoSub", Household.class);
                if (!householdInfo.isTuoPinBuWenDing()){
                    householdInfo.setTuoPinBuWenDingType(null);
                }
                householdMapper.updateById(householdInfo);
                break;
        }

        return new CommonResult<>(ErrorCode.SUCCESS);
    }


    @PostMapping("/setPrincipal")
    public CommonResult<Void> setPrincipal(@RequestBody JSONObject object) {

        List<Integer> userIds = object.getJSONArray("userIds").toJavaList(Integer.class);
        Integer deptId = object.getInteger("deptId");
        for (Integer userId : userIds) {
            deptPrincipalMapper.insert(new DeptPrincipal().setUserId(userId).setDeptId(deptId));
        }
        return new CommonResult<>(ErrorCode.SUCCESS);
    }


    @PostMapping("/removePrincipal/{deptId}/{userId}")
    public CommonResult<Void> removePrincipal(@PathVariable Integer deptId, @PathVariable Integer userId) {
        QueryWrapper<DeptPrincipal> deptPrincipalQueryWrapper = new QueryWrapper<>();
        deptPrincipalQueryWrapper.eq("dept_id", deptId);
        deptPrincipalQueryWrapper.eq("user_id", userId);
        deptPrincipalMapper.delete(deptPrincipalQueryWrapper);

        return new CommonResult<>(ErrorCode.SUCCESS);
    }


    @PostMapping("/getPrincipal/{deptId}")
    public CommonResult<List<BaseInfo>> getPrincipal(@PathVariable Integer deptId) {
        QueryWrapper<DeptPrincipal> deptPrincipalQueryWrapper = new QueryWrapper<>();
        deptPrincipalQueryWrapper.eq("dept_id", deptId);
        List<DeptPrincipal> deptPrincipals = deptPrincipalMapper.selectList(deptPrincipalQueryWrapper);
        List<Integer> integers = deptPrincipals.stream().map(deptPrincipal -> deptPrincipal.getUserId()).collect(Collectors.toList());
        List<BaseInfo> nameAndIdById = CommonUtils.getNameAndIdById(integers);
        return new CommonResult<>(ErrorCode.SUCCESS, nameAndIdById);
    }

    @GetMapping("/getTownshipInfo/{id}")
    public CommonResult<Township> getTownshipInfo(@PathVariable Integer id) {
        QueryWrapper<Township> townshipQueryWrapper = new QueryWrapper<>();
        if (id == 999999999){
            QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
            deptQueryWrapper.eq("dept_level",0);
            Dept dept = deptMapper.selectOne(deptQueryWrapper);
            townshipQueryWrapper.eq("dept_id", dept.getDeptId());
            Township township = townshipMapper.selectOne(townshipQueryWrapper);

            return new CommonResult<>(ErrorCode.SUCCESS,township);
        }


        townshipQueryWrapper.eq("dept_id", id);
        Township township = townshipMapper.selectOne(townshipQueryWrapper);
        return new CommonResult<>(ErrorCode.SUCCESS, township);
    }

    @GetMapping("/getVillageInfo/{id}")
    public CommonResult<Village> getVillageInfo(@PathVariable Integer id) {
        QueryWrapper<Village> villageQueryWrapper = new QueryWrapper<>();
        villageQueryWrapper.eq("dept_id", id);
        Village village = villageMapper.selectOne(villageQueryWrapper);
        return new CommonResult<>(ErrorCode.SUCCESS, village);
    }

    @GetMapping("/getSocietyInfo/{id}")
    public CommonResult<Society> getSocietyInfo(@PathVariable Integer id) {
        QueryWrapper<Society> societyQueryWrapper = new QueryWrapper<>();
        societyQueryWrapper.eq("dept_id", id);
        Society society = societyMapper.selectOne(societyQueryWrapper);
        return new CommonResult<>(ErrorCode.SUCCESS, society);
    }

    @GetMapping("/getHouseholdInfo/{id}")
    public CommonResult<Household> getHouseholdInfo(@PathVariable Integer id) {
        QueryWrapper<Household> householdQueryWrapper = new QueryWrapper<>();
        householdQueryWrapper.eq("dept_id", id);
        Household household = householdMapper.selectOne(householdQueryWrapper);
        return new CommonResult<>(ErrorCode.SUCCESS, household);
    }

    public DeptTree getTreeById(Dept dept) {

        DeptTree deptTree = new DeptTree();
        deptTree.setCanEdit(1);
        deptTree.setId(dept.getDeptId());
        deptTree.setLabel(dept.getDeptName());
        deptTree.setDeptLevel(dept.getDeptLevel());
//        deptTree.setDisabled(dept.getDeptLevel() == 0 || dept.getDeptLevel() == 1 ||dept.getDeptLevel() == 2);
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

    public List<Integer> getDeptIds(Integer id) {
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
        deptQueryWrapper.eq("dept_parent_id", id);
        List<Dept> depts = deptMapper.selectList(deptQueryWrapper);
        if (depts.size() > 0) {
            for (Dept dept : depts) {
                List<Integer> deptIds = getDeptIds(dept.getDeptId());
                ids.addAll(deptIds);
            }
        }
        return ids;
    }

    public List<DeptTree> getNeedTree(List<Integer> needIds, DeptTree deptTree) {
        List<DeptTree> deptTrees = new ArrayList<>();
        boolean flag = false;
        for (Integer needId : needIds) {
            if (needId == deptTree.getId()) {
                deptTrees.add(deptTree);
                flag = true;
            }
        }
        if (flag) {
            return deptTrees;
        } else {
            if (deptTree.getChildren() != null && deptTree.getChildren().size() > 0) {
                for (DeptTree child : deptTree.getChildren()) {
                    if (getNeedTree(needIds, child) != null) {
                        deptTrees.addAll(getNeedTree(needIds, child));
                    }
                }
                return deptTrees;
            }

        }

        return null;


    }

}
