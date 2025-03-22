package com.ent.codoa.pojo.req.leavemanagement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class LeaveManagementAdd implements Serializable {
    private static final long serialVersionUID = -1744595239378847574L;

    @NotNull(message = "申请开始休假时间不能为空")
    @ApiModelProperty("申请开始休假日期")
    private LocalDate startDate;

    @NotNull(message = "申请结束休假时间不能为空")
    @ApiModelProperty("申请结束休假日期 即结束休假的日子")
    private LocalDate endDate;

}
