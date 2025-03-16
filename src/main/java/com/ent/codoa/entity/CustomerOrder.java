package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.OrderStatusEnum;
import com.ent.codoa.common.constant.enums.PaymentStatusEnum;
import com.ent.codoa.entity.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("customer_order")
public class CustomerOrder extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 4701719975718071979L;

    @ApiModelProperty("总价")
    private BigDecimal amount;
    @ApiModelProperty("利润")
    private BigDecimal profit;
    @ApiModelProperty("介绍")
    private String introduce;
    @ApiModelProperty("订单编号")
    private String orderNum;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("数量")
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
    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;
    @ApiModelProperty("所属系统用户")
    private String systemClientAccount;

}
