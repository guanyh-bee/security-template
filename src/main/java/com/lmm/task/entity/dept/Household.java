package com.lmm.task.entity.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

//户信息实体类
@Data
@Accessors(chain = true)
public class Household {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String householdOrder;
    private String householdAddr;
    private String takeInSource;
    private boolean diBao;
    private boolean liDangJianKa;
    private boolean wuBao;
    private boolean tuoPinBuWenDing;
    private String tuoPinBuWenDingType;
    private Integer deptId;
}
