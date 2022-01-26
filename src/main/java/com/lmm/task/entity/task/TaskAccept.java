package com.lmm.task.entity.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskAccept {
    private Integer[] taskPerson;
    private boolean canDivide;
    private String taskName;
    private Integer[] taskOwnItem;
    private Integer[] taskPerson2;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime untilTime;
    private Integer type;
}
