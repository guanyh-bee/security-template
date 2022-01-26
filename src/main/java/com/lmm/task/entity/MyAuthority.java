package com.lmm.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("authority")
public class MyAuthority {
    private Integer id;
    private String authorityName;
    private String url;
    private boolean enable;
}
