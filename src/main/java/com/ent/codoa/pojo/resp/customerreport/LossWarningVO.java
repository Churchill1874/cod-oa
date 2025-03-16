package com.ent.codoa.pojo.resp.customerreport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LossWarningVO implements Serializable {
    private static final long serialVersionUID = 7612185743409390078L;

    @ApiModelProperty("订单对比数据")
    private LossReasonData orderComparison;

    @ApiModelProperty("投诉对比数据")
    private LossReasonData complaintComparison;


}
