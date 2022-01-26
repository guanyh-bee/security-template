package com.lmm.task.entity.role;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserRoleVO {
    private Integer userId;
    private Integer deptId;
    private String account;
    private String realName;
    private String deptName;
    private List<Role> roles;
    private boolean enabled;
}
