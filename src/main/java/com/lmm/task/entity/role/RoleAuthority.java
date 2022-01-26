package com.lmm.task.entity.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleAuthority {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer authorityId;
}
