package com.lmm.task.entity.task;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TaskItem {
    @TableId(type = IdType.AUTO)
    private Integer taskItemId;
    private String taskItemName;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String taskItemUnit;
    private boolean taskItemNumber;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modified;
    @TableField(fill = FieldFill.INSERT)
    private Integer createdBy;
    private String prop;
}
