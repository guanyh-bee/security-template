package com.lmm.task.entity.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Dept {
    @TableId(type = IdType.AUTO)
    private Integer deptId;
    private String deptName;
    private Integer deptParentId;
    private Integer deptLevel;
}
