package com.ent.codoa.pojo.req.performanceappraisal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class PerformanceAppraisalAdd implements Serializable {
    private static final long serialVersionUID = 3398334057027580286L;

    @NotBlank(message = "员工账号不能为空")
    @ApiModelProperty(value = "员工账号",required = true)
    private String account;

    @NotBlank(message = "评价结果不能为空")
    @ApiModelProperty(value = "评价结果 A,B,C,D",required = true)
    private String level;

    @NotBlank(message = "评价内容不能为空")
    @ApiModelProperty(value = "评价内容存json字符串",required = true)
    private String appraisal;

    @NotBlank(message = "月份 例如 2020-01 ,年-月 没有日")
    @ApiModelProperty(value = "考核的月份 格式 2020-01 年-月 没有日", required = true)
    private String date;

}
