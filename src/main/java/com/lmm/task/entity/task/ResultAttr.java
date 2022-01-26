package com.lmm.task.entity.task;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultAttr {
    String label;
    String prop;
    Object value;
}
