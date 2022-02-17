package com.lmm.task.entity.task;

import com.lmm.task.entity.MyUserBaseInfo;
import com.lmm.task.entity.user.SpecialIdentity;
import com.lmm.task.entity.user.WorkCircumstances;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TaskResultVO {
    private Integer taskResultId;
    private Integer taskId;
    private Integer taskStatus;
    private List<ResultData> resultData;
    private Integer taskUserId;
    private Integer checkStep;
    private MyUserBaseInfo info;
    private SpecialIdentity specialIdentity;
    private WorkCircumstances workCircumstances;
}
