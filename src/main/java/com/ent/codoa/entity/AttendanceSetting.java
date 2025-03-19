package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("attendance_setting")
public class AttendanceSetting extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 6382449441209435243L;

    @ApiModelProperty("上班时间")
    private String startWorkTime;
    @ApiModelProperty("下班时间")
    private String endWorkTime;
    @ApiModelProperty("所属系统管理账号")
    private String systemClientAccount;


}
