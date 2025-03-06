package com.ent.codoa.pojo.req.customerorder;

import com.ent.codoa.common.constant.enums.OrderStatusEnum;
import com.ent.codoa.common.constant.enums.PaymentStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CustomerOrderInfoUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = 7055745893854343931L;

    @ApiModelProperty("支付状态")
    private PaymentStatusEnum payStatus;
    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    @ApiModelProperty("备注")
    private String remark;
    @NotNull(message = "订单状态不能为空")
    @ApiModelProperty(value = "订单状态",required = true)
    private OrderStatusEnum status;

}
