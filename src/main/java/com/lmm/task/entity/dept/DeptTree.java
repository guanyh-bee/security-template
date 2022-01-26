package com.lmm.task.entity.dept;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DeptTree {
    private Integer id;
    private String label;
    private boolean disabled;
    private Integer deptLevel;
    private List<DeptTree> children;
    //1可以 0不可以
    private Integer canEdit;
}
