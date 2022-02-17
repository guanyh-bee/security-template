package com.lmm.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.promeg.pinyinhelper.Pinyin;
import com.lmm.task.entity.MyUserBaseInfo;
import com.lmm.task.entity.task.*;
import com.lmm.task.entity.user.SpecialIdentity;
import com.lmm.task.entity.user.WorkCircumstances;
import com.lmm.task.mapper.MyUserMapper;
import com.lmm.task.mapper.task.*;
import com.lmm.task.mapper.user.SpecialIdentityMapper;
import com.lmm.task.mapper.user.WorkCircumstancesMapper;
import com.lmm.task.utils.SecurityUtils;
import com.lmm.task.utils.errorCode.CommonResult;
import com.lmm.task.utils.errorCode.ErrorCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskItemMapper taskItemMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    TaskOwnMapper taskOwnMapper;
    @Autowired
    TaskResultMapper taskResultMapper;
    @Autowired
    ResultDataMapper resultDataMapper;
    @Autowired
    WorkCircumstancesMapper workCircumstancesMapper;
    @Autowired
    SpecialIdentityMapper specialIdentityMapper;

    @Autowired
    MyUserMapper userMapper;

    @GetMapping("/getItem")
    public CommonResult<List<TaskItem>> getItem(){
        List<TaskItem> taskItems = taskItemMapper.selectList(null);
        return new CommonResult(ErrorCode.SUCCESS,taskItems);
    }

    @PostMapping("/addItem")
    public CommonResult addItem(@RequestBody TaskItem taskItem){
        if (taskItem.getTaskItemUnit() == null || taskItem.getTaskItemUnit() == ""){
            taskItem.setTaskItemUnit(null);
        }
        taskItem.setProp(Pinyin.toPinyin(taskItem.getTaskItemName(),"").toLowerCase(Locale.ROOT)+UUID.randomUUID());
        taskItemMapper.insert(taskItem);
        return new CommonResult(ErrorCode.SUCCESS);
    }

    @PostMapping("/editItem")
    public CommonResult editItem(@RequestBody TaskItem taskItem){
        taskItem.setProp(Pinyin.toPinyin(taskItem.getTaskItemName(),"").toLowerCase(Locale.ROOT)+UUID.randomUUID());
if (taskItem.getTaskItemUnit() == ""){
    taskItem.setTaskItemUnit(null);
}
        taskItemMapper.updateById(taskItem);
        return new CommonResult(ErrorCode.SUCCESS);
    }

    @PostMapping("/deleteItem")
    public CommonResult deleteItem(@RequestBody TaskItem taskItem){
        taskItemMapper.deleteById(taskItem.getTaskItemId());
        return new CommonResult(ErrorCode.SUCCESS);
    }

    //新建任务
    @PostMapping("/newTask")
    @Transactional
    public CommonResult newTask(@RequestBody TaskAccept accept){
        Task task = new Task().setTaskName(accept.getTaskName()).setCanDivide(accept.isCanDivide()).setUntilTime(accept.getUntilTime());
        taskMapper.insert(task);
        System.out.println(accept.getTaskOwnItem().length);
        for (Integer integer : accept.getTaskOwnItem()) {
                taskOwnMapper.insert(new TaskOwnItem().setTaskId(task.getTaskId()).setTaskItemId(integer));
        }

        if (accept.getType() == 1){
            for (Integer taskPerson : accept.getTaskPerson()) {
                taskResultMapper.insert(new TaskResult().setTaskId(task.getTaskId()).setTaskUserId(taskPerson));
            }
        }else if (accept.getType() == 2){
            for (Integer taskPerson : accept.getTaskPerson2()) {
                taskResultMapper.insert(new TaskResult().setTaskId(task.getTaskId()).setTaskUserId(taskPerson));
            }
        }
        return new CommonResult(ErrorCode.SUCCESS);
    }

    //我的待完成任务
    @GetMapping("/myTask")
    public CommonResult<List<MyTaskVO>> myTask(){
        QueryWrapper<TaskResult> taskResultQueryWrapper = new QueryWrapper<>();
        taskResultQueryWrapper.eq("task_user_id", SecurityUtils.getUserId());
        taskResultQueryWrapper.eq("task_status", 0);
        List<TaskResult> taskResults = taskResultMapper.selectList(taskResultQueryWrapper);

        List<MyTaskVO> taskVOList = taskResults.stream().map(taskResult -> {
            MyTaskVO myTaskVO = new MyTaskVO();
            myTaskVO.setTaskResultId(taskResult.getTaskResultId());
            myTaskVO.setTaskId(taskResult.getTaskId());
            myTaskVO.setMyUserId(taskResult.getTaskUserId());
            Task task = taskMapper.selectById(taskResult.getTaskId());
            myTaskVO.setTaskCreatedTime(task.getCreated());
            myTaskVO.setTaskName(task.getTaskName());
            myTaskVO.setTaskUntilTime(task.getUntilTime());
            if (task.getUntilTime().isAfter(LocalDateTime.now())){
                myTaskVO.setExpired(false);
            }else {
                myTaskVO.setExpired(true);
            }
            long l = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
            long l1 = task.getUntilTime().toEpochSecond(ZoneOffset.ofHours(8));
            long lll = l1-l;
            if (lll/60/60<24 && lll/60/60>0){
                myTaskVO.setWarning(true);
            }else {
                myTaskVO.setWarning(false);
            }

            QueryWrapper<TaskOwnItem> taskOwnItemQueryWrapper = new QueryWrapper<>();
            taskOwnItemQueryWrapper.eq("task_id", taskResult.getTaskId());
            List<TaskOwnItem> taskOwnItems = taskOwnMapper.selectList(taskOwnItemQueryWrapper);
            List<Integer> collect = taskOwnItems.stream().map(taskOwnItem -> taskOwnItem.getTaskItemId()).collect(Collectors.toList());
            List<TaskItem> taskItems = taskItemMapper.selectBatchIds(collect);
            myTaskVO.setItems(taskItems);
            return myTaskVO;
        }).collect(Collectors.toList());

        return new CommonResult<>(ErrorCode.SUCCESS,taskVOList);
    }
//提交任务
    @PostMapping("/commitMyTask")
    @Transactional
    public CommonResult commitMyTask(@RequestBody JSONObject object){
        List<ResultData> commitData = object.getJSONArray("commitData").toJavaList(ResultData.class);
        for (ResultData data : commitData) {
            resultDataMapper.insert(data);
        }

        Integer taskResultId = commitData.get(0).getTaskResultId();
        TaskResult taskResult = new TaskResult();
        taskResult.setTaskResultId(taskResultId);
        taskResult.setTaskStatus(1);

        taskResultMapper.updateById(taskResult);

        TaskResult taskResult1 = taskResultMapper.selectById(taskResultId);
        QueryWrapper<TaskResult> taskResultQueryWrapper = new QueryWrapper<>();
        taskResultQueryWrapper.eq("task_id",taskResult1.getTaskId());
        taskResultQueryWrapper.eq("task_status",0);
        List<TaskResult> taskResults = taskResultMapper.selectList(taskResultQueryWrapper);

        if(taskResults.size()<1){
            Task task = new Task();
            task.setTaskId(taskResult1.getTaskId());
            task.setTaskStatus(1);
            taskMapper.updateById(task);
        }


        return new CommonResult(ErrorCode.SUCCESS);
    }

    //我发布的任务
    @GetMapping("/published")
    public CommonResult<List<Task>> publishedTask(){
        Integer userId = SecurityUtils.getUserId();
        QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
        taskQueryWrapper.eq("created_by",userId);
        List<Task> tasks = taskMapper.selectList(taskQueryWrapper);


        return new CommonResult<>(ErrorCode.SUCCESS,tasks);

    }

    //获取任务对应结果
    @GetMapping("/getResults/{id}")
    public CommonResult<List<TaskResult>> getResults(@PathVariable Integer id){
        QueryWrapper<TaskResult> taskResultQueryWrapper = new QueryWrapper<>();
        taskResultQueryWrapper.eq("task_id",id);
        List<TaskResult> taskResults = taskResultMapper.selectList(taskResultQueryWrapper);
        List<TaskResult> results = taskResults.stream().map(taskResult -> {
            QueryWrapper<ResultData> resultDataQueryWrapper = new QueryWrapper<>();
            resultDataQueryWrapper.eq("task_result_id", taskResult.getTaskResultId());
            List<ResultData> resultData = resultDataMapper.selectList(resultDataQueryWrapper);
            List<ResultData> collect = resultData.stream().map(resultData1 -> {
                TaskItem taskItem = taskItemMapper.selectById(resultData1.getTaskItemId());
                resultData1.setTaskItem(taskItem);
                return resultData1;
            }).collect(Collectors.toList());
            taskResult.setResultData(collect);
            return taskResult;
        }).collect(Collectors.toList());


        List<TaskResultVO> collect = results.stream().map(taskResult -> {
            TaskResultVO taskResultVO = new TaskResultVO();
            BeanUtils.copyProperties(taskResult,taskResultVO);

            MyUserBaseInfo userInfo = userMapper.getUserInfo(taskResult.getTaskUserId());
            QueryWrapper<SpecialIdentity> specialIdentityQueryWrapper = new QueryWrapper<>();
            specialIdentityQueryWrapper.eq("user_id",userInfo.getUserId());
            SpecialIdentity specialIdentity = specialIdentityMapper.selectOne(specialIdentityQueryWrapper);
            QueryWrapper<WorkCircumstances> workCircumstancesQueryWrapper = new QueryWrapper<>();
            workCircumstancesQueryWrapper.eq("user_id",userInfo.getUserId());
            WorkCircumstances workCircumstances = workCircumstancesMapper.selectOne(workCircumstancesQueryWrapper);
            taskResultVO.setSpecialIdentity(specialIdentity);
            taskResultVO.setWorkCircumstances(workCircumstances);

            userInfo.setDeptName(SecurityUtils.getWholeDeptName(userInfo.getDeptId()));
            taskResultVO.setInfo(userInfo);
            return taskResultVO;
        }).collect(Collectors.toList());



        return new CommonResult(ErrorCode.SUCCESS,collect);


    }


    public String changeData(String data){
        JSONObject object = JSONObject.parseObject(data);
        Map<String, Object> innerMap = object.getInnerMap();

        ArrayList<Map.Entry<String, Object>> entries = new ArrayList<>(innerMap.entrySet());
        Collections.sort(entries, Comparator.comparing(Map.Entry::getKey));
        List<ResultAttr> resultAttrs = new ArrayList<>();
        entries.stream().forEach(e->{
            int i = Integer.parseInt(e.getKey().substring(0, 1));
            TaskItem taskItem = taskItemMapper.selectById(i);
            ResultAttr resultAttr = new ResultAttr();
            int v = Integer.parseInt(e.getKey().substring(e.getKey().length()-1));
            if (v == 1){
               resultAttr.setLabel("是否有"+taskItem.getTaskItemName());
               resultAttr.setProp(taskItem.getProp()+1);
            }else {
                resultAttr.setLabel(taskItem.getTaskItemName()+"数量");
                resultAttr.setProp(taskItem.getProp()+2);
            }
            resultAttr.setValue(e.getValue());
            resultAttrs.add(resultAttr);

        });
        return  JSONObject.toJSONString(resultAttrs);
    }
}
