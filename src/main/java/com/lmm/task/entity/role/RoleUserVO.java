package com.lmm.task.entity.role;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoleUserVO {
    private Integer roleId;
    private String roleName;
    List<SimpleUser> users;



}
