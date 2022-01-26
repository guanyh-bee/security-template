package com.lmm.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseInfo {
    @TableId(type = IdType.AUTO)
    private Integer baseInfoId;
    private Integer deptId;
    private String phone;
    private String realName;
    private String addr;
    @TableField(exist = false)
    private String account;
    private Integer myUserId;
    @TableField(exist = false)
    private String deptName;
    private boolean isHost;
    private String relation;
    private Integer gender;
    private String idCardNum;
    private String nation;
    private String jvZhuQingKuang;
    @TableField(exist = false)
    private Integer age;
}
