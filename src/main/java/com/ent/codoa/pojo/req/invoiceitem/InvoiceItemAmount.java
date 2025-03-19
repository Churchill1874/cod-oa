package com.ent.codoa.pojo.req.invoiceitem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InvoiceItemAmount implements Serializable {
    private static final long serialVersionUID = 8894015260839082225L;

    @Length(max = 30, message = "内容100字以内")
    @ApiModelProperty(value = "商品或服务的内容")
    private String description;

    @NotNull(message = "商品或服务的数量不能为空")
    @ApiModelProperty(value = "商品或服务的数量",required = true)
    private Integer quantity;

    @ApiModelProperty(value = "商品或服务的单位")
    private String unit;

    @NotNull(message = "商品单价不能为空")
    @ApiModelProperty(value = "商品或服务的单价（不含税）",required = true)
    private BigDecimal unitPrice;
}
