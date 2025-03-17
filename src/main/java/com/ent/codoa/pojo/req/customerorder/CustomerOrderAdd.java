package com.ent.codoa.pojo.req.customerorder;

import com.ent.codoa.common.constant.enums.OrderStatusEnum;
import com.ent.codoa.common.constant.enums.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CustomerOrderAdd implements Serializable {
    private static final long serialVersionUID = 2564153518774583176L;

    @ApiModelProperty("总价")
    private BigDecimal amount;
    @ApiModelProperty("利润")
    private BigDecimal profit;
    @ApiModelProperty("介绍")
    private String introduce;
    @ApiModelProperty("订单编号")
    private String orderNum;
    @NotNull(message = "名字不能为空")
    @ApiModelProperty(value = "名称",required = true)
    private String name;
    @NotNull(message = "账号不能为空")
    @ApiModelProperty("账号")
    private String account;
    @NotNull(message = "数量不能为空")
    @ApiModelProperty(value = "数量",required = true)
    private Integer count;
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;
    @ApiModelProperty("支付状态")
    private PaymentStatusEnum payStatus;
    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    @ApiModelProperty("备注")
    private String remark;
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "订单状态",required = true)
    private OrderStatusEnum status;

}
