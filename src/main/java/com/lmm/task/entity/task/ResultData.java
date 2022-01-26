package com.lmm.task.entity.task;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultData {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer taskItemId;
    boolean hasItem;
    Integer itemNum;
    Integer taskResultId;
    @TableField(exist = false)
    TaskItem taskItem;
}
