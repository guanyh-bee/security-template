package com.lmm.task.entity.role;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleUser {
    private Integer userId;
    private String realName;

}
