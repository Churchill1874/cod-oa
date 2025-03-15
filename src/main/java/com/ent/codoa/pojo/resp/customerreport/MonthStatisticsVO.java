package com.ent.codoa.pojo.resp.customerreport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MonthStatisticsVO implements Serializable {
    private static final long serialVersionUID = 3809971605302926406L;

    @ApiModelProperty("月份 如1月 2月 3月 4月")
    private Integer date;

    @ApiModelProperty("统计结果 数字")
    private String value;

}
