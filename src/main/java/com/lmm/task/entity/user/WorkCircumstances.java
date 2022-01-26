package com.lmm.task.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WorkCircumstances {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String workType;
    private String workArea;
    private Integer userId;
    private String workAddr;
}
