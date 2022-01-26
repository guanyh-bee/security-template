package com.lmm.task.mapper.task;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmm.task.entity.task.Task;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
}
