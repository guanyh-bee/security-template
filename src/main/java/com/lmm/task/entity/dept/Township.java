package com.lmm.task.entity.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

//乡镇信息实体类
@Data
@Accessors(chain = true)
public class Township {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String description;
    private Integer deptId;
}
