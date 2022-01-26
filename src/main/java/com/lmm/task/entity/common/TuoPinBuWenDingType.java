package com.lmm.task.entity.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TuoPinBuWenDingType {
    private Integer id;
    private String label;
}
