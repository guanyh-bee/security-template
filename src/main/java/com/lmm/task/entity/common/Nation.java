package com.lmm.task.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Nation {
    @TableId(type = IdType.AUTO)
    private Integer nationId;
    private String nationName;
}
