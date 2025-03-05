package com.ent.codoa.pojo.req.customerorder;

import com.ent.codoa.common.constant.enums.OrderStatusEnum;
import com.ent.codoa.common.constant.enums.PaymentStatusEnum;
import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerOrderPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -6192387915098316206L;

    @ApiModelProperty("订单编号")
    private String orderNum;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("支付状态")
    private PaymentStatusEnum payStatus;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;

}
