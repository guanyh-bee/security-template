package com.lmm.task.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MyUserBaseInfo {
    private Integer baseInfoId;
    private Integer userId;
    private String deptName;
    private Integer deptId;
    private List<String> roleName;
    private String addr;
    private String phone;
    private String realName;
    private List<Integer> roleLevel;
    private String account;
    private Integer gender;
    private String relation;
    private boolean isHost;
    private String idCardNum;
    private String nation;
    private String jvZhuQingKuang;
    private List<String> listAuthorities;

}
