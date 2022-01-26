package com.lmm.task.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WorkArea {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String label;
}
