package com.lmm.task.controller;

import com.lmm.task.entity.common.*;
import com.lmm.task.mapper.common.*;
import com.lmm.task.utils.errorCode.CommonResult;
import com.lmm.task.utils.errorCode.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonController {
    @Autowired
    private NationMapper nationMapper;
    @Autowired
    private TakeInSourceTypeMapper takeInSourceTypeMapper;
    @Autowired
    private TuoPinBuWenDingTypeMapper tuoPinBuWenDingTypeMapper;
    @Autowired
    private WorkAreaMapper workAreaMapper;
    @Autowired
    private WorkTypeMapper workTypeMapper;

    @GetMapping("/getNations")
    public CommonResult<List<Nation>> getNations(){
        List<Nation> nations = nationMapper.selectList(null);
        return new CommonResult<>(ErrorCode.SUCCESS,nations);
    }
    @GetMapping("/getTakeInSourceTypes")
    public CommonResult<List<TakeInSourceType>> getTakeInSourceTypes(){
        List<TakeInSourceType> takeInSourceTypes = takeInSourceTypeMapper.selectList(null);
        return new CommonResult<>(ErrorCode.SUCCESS,takeInSourceTypes);
    }
    @GetMapping("/getTuoPinBuWenDingTypes")
    public CommonResult<List<TuoPinBuWenDingType>> getTuoPinBuWenDingTypes(){
        List<TuoPinBuWenDingType> tuoPinBuWenDingTypes = tuoPinBuWenDingTypeMapper.selectList(null);
        return new CommonResult<>(ErrorCode.SUCCESS,tuoPinBuWenDingTypes);
    }
    @GetMapping("/getWorkAreas")
    public CommonResult<List<WorkArea>> getWorkAreas(){
        List<WorkArea> workAreas = workAreaMapper.selectList(null);
        return new CommonResult<>(ErrorCode.SUCCESS,workAreas);
    }
    @GetMapping("/getWorkTypes")
    public CommonResult<List<WorkType>> getWorkTypes(){
        List<WorkType> workTypes = workTypeMapper.selectList(null);
        return new CommonResult<>(ErrorCode.SUCCESS,workTypes);
    }

}
