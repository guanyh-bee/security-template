package com.lmm.task.entity.task;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TaskResult {
    @TableId(type = IdType.AUTO)
    private Integer taskResultId;
    private Integer taskId;
    private Integer taskStatus;
    private Integer taskUserId;
    private Integer checkStep;
    @TableField(exist = false)
    private List<ResultData> resultData;
}
