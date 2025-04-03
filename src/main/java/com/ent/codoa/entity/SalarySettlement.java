package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("salary_settlement")
public class SalarySettlement extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 3482845269443219599L;

    @ApiModelProperty("工资所属月份")
    private String Date;

    @ApiModelProperty("员工账号")
    private String account;

    @ApiModelProperty("基本工资")
    private BigDecimal baseSalary;

    @ApiModelProperty("工作日加班费比例")
    private BigDecimal weekdayOvertimePayRate;

    @ApiModelProperty("平日加班费")
    private BigDecimal weekdayOvertimeAmount;

    @ApiModelProperty("周末加班费比例")
    private BigDecimal weekendOvertimePayRate;

    @ApiModelProperty("周末加班费")
    private BigDecimal weekendOvertimeAmount;

    @ApiModelProperty("应该出勤天数")
    private BigDecimal expectedAttendance;

    @ApiModelProperty("实际出勤天数")
    private BigDecimal actualAttendance;

    @ApiModelProperty("缺勤天数")
    private BigDecimal absenceDays;

    @ApiModelProperty("迟到时间总计 单位小时")
    private BigDecimal lateHours;

    @ApiModelProperty("早退时间总计 单位小时")
    private BigDecimal leaveEarly;

    @ApiModelProperty("平日加班时间 单位小时")
    private BigDecimal weekdaysOvertime;

    @ApiModelProperty("周末加班时间 单位小时")
    private BigDecimal weekendsOvertime;

    @ApiModelProperty("绩效评分")
    private String performanceScore;

    @ApiModelProperty("额外计算金额")
    private BigDecimal extraCalculationAmount;

    @ApiModelProperty("额外计算金额原因备注")
    private String remark;

    @ApiModelProperty("系统用户账号")
    private String systemClientAccount;

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
