package com.lmm.task.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
//用户特殊身份
@Data
@Accessors(chain = true)
public class SpecialIdentity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private boolean isDiBao;
    private boolean isWuBao;
    private boolean isCanJi;
    private boolean isDangYuan;
    private boolean isTuiWuJunRen;
    private double diBaoMoney;
    private double wuBaoMoney;
    private Integer canJiLevel;
    private double canJiMoney;
    private Integer userId;
}
