package com.ent.codoa.pojo.resp.customerreport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerReportVO implements Serializable {
    private static final long serialVersionUID = -924639774935048531L;

    @ApiModelProperty("本周统计")
    private String thisWeek = "0.00";

    @ApiModelProperty("上周统计")
    private String lastWeek = "0.00";

    @ApiModelProperty("本年月份统计集合")
    private List<MonthStatisticsVO> thisYearMonthList;

    @ApiModelProperty("本年统计")
    private String thisYear = "0.00";

    @ApiModelProperty("去年统计")
    private String lastYear = "0.00";


}
