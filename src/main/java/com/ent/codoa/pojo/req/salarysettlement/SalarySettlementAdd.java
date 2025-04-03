package com.ent.codoa.pojo.req.salarysettlement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SalarySettlementAdd implements Serializable {
    private static final long serialVersionUID = 4300109050063251921L;

    @NotBlank(message = "工资所属月份不能为空")
    @ApiModelProperty(value = "工资所属月份",required = true)
    private String Date;

    @NotBlank(message = "员工账号不能为空")
    @ApiModelProperty(value = "员工账号",required = true)
    private String account;

    @ApiModelProperty("基本工资")
    private BigDecimal baseSalary;

    @NotNull(message = "工作日加班费比例不能为空")
    @ApiModelProperty(value = "工作日加班费比例  例如:1就是没有 1.1就是1.1倍的意思, 2就是两倍的意思",required = true)
    private BigDecimal weekdayOvertimePayRate;

    @ApiModelProperty("平日加班费")
    private BigDecimal weekdayOvertimeAmount;

    @NotNull(message = "周末加班费用比例不能为空")
    @ApiModelProperty(value = "周末加班费比例 例如:1就是没有 1.1就是1.1倍的意思, 2就是两倍的意思",required = true)
    private BigDecimal weekendOvertimePayRate;

    @ApiModelProperty("周末加班费")
    private BigDecimal weekendOvertimeAmount;

    @NotNull(message = "应该出勤天数不能为空")
    @ApiModelProperty(value = "应该出勤天数",required = true)
    private BigDecimal expectedAttendance;

    @NotNull(message = "实际出勤天数不能为空")
    @ApiModelProperty(value = "实际出勤天数",required = true)
    private BigDecimal actualAttendance;

    @ApiModelProperty("缺勤天数")
    private BigDecimal absenceDays = BigDecimal.ZERO;

    @ApiModelProperty("迟到时间总计 单位小时")
    private BigDecimal lateHours = BigDecimal.ZERO;

    @ApiModelProperty("早退时间总计 单位小时")
    private BigDecimal leaveEarly;

    @ApiModelProperty("平日加班时间 单位小时")
    private BigDecimal weekdaysOvertime = BigDecimal.ZERO;

    @ApiModelProperty("周末加班时间 单位小时")
    private BigDecimal weekendsOvertime = BigDecimal.ZERO;

    @ApiModelProperty("额外计算金额")
    private BigDecimal extraCalculationAmount = BigDecimal.ZERO;

    @ApiModelProperty("额外计算金额原因备注")
    private String remark;

    @ApiModelProperty("绩效评分")
    private String performanceScore;

    @ApiModelProperty("所得税交税比例")
    private Integer payTaxesRate;

    @ApiModelProperty("所得税交税金额")
    private BigDecimal payTaxesAmount;

    @ApiModelProperty("住民税比例")
    private Integer residentTaxRate;

    @ApiModelProperty("住民税金额")
    private BigDecimal residentTaxAmount;

    @ApiModelProperty("估算工资")
    private BigDecimal estimateSalary;



}
