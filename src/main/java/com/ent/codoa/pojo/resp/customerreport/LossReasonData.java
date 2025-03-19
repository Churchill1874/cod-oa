package com.ent.codoa.pojo.resp.customerreport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LossReasonData implements Serializable {
    private static final long serialVersionUID = -8287255488630438106L;

    @ApiModelProperty("上一个月日期")
    private String lastMonthDate;

    @ApiModelProperty("上一个月数量总计")
    private long lastMonthCount = 0;

    @ApiModelProperty("上上个月日期")
    private String twoMonthAgoDate;

    @ApiModelProperty("上上个月数量总计")
    private long twoMonthAgoCount = 0;

    @ApiModelProperty(value = "上个月比上上个月变动比例", notes = "正数代表增加了多少比例, 负数就代表减少了多少")
    private int rate = 0;

}
