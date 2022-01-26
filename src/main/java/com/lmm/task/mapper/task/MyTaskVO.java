package com.lmm.task.mapper.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lmm.task.entity.task.TaskItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class MyTaskVO {
    public String taskName;
    public Integer taskResultId;
    public Integer taskId;
    public List<TaskItem> items;
    public Integer myUserId;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    public LocalDateTime taskCreatedTime;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    public LocalDateTime taskUntilTime;
    public boolean expired;
    public boolean warning;
}
