package com.lmm.task.entity.role;

import com.lmm.task.entity.MyAuthority;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoleAuthorityVO {
    private Role role;
    private List<MyAuthority> authorities;
}
