package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("leave_management")
public class LeaveManagement extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -8795972895668962380L;

    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("状态 待审批 / 通过 / 拒绝")
    private String status;
    @ApiModelProperty("申请开始休假日期")
    private LocalDate startDate;
    @ApiModelProperty("申请结束休假日期")
    private LocalDate endDate;
    @ApiModelProperty("休假天数统计")
    private Integer leaveDaysCount;
    @ApiModelProperty("审批账号")
    private String approvalAccount;
    @ApiModelProperty("审批时间")
    private LocalDateTime approvalTime;
    @ApiModelProperty("系统用户账号")
    private String systemClientAccount;


}
