package com.ent.codoa.pojo.req.invoiceitem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InvoiceItemTax implements Serializable {
    private static final long serialVersionUID = 4825097164893931426L;

    @NotNull(message = "商品税率不能为空")
    @ApiModelProperty(value = "商品或服务的税率（如8%或10%）",required = true)
    private Integer taxRate;

    @NotNull(message = "商品总金额不能为空")
    @ApiModelProperty(value = "商品或服务的总金额（不含税）",required = true)
    private BigDecimal amount;
}
