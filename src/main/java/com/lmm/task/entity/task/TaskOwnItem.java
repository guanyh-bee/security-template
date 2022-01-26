package com.lmm.task.entity.task;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TaskOwnItem {
    @TableId(type = IdType.AUTO)
    private Integer taskOwnItemId;
    private Integer taskItemId;
    private Integer taskId;
}
