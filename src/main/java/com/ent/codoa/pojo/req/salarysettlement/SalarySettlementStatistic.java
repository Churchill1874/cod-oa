package com.ent.codoa.pojo.req.salarysettlement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SalarySettlementStatistic implements Serializable {
    private static final long serialVersionUID = -3651698688139041500L;

    @NotBlank(message = "指定日期不能为空")
    @ApiModelProperty(value = "指定日期 年-月 没有日 ,例如 2025-01 .注意 月份小于10要补0", required = true)
    private String date;

    @NotBlank(message = "员工账号不能为空")
    @ApiModelProperty(value = "员工账号", required = true)
    private String account;

}
