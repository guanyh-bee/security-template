package com.lmm.task.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MyUserBaseVO {
    private String realName;
    private String account;
    private Integer userId;
    private boolean disabled;
}
