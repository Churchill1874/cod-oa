package com.ent.codoa.pojo.req.invoiceitem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceItemAdd implements Serializable {
    private static final long serialVersionUID = 4062071854025699964L;

    @Length(max = 30, message = "内容100字以内")
    @NotBlank(message = "商品内容不能为空")
    @ApiModelProperty(value = "商品或服务的内容",required = true)
    private String description;


    @NotNull(message = "商品或服务的数量不能为空")
    @ApiModelProperty(value = "商品或服务的数量",required = true)
    private Integer quantity;

    @Length(max = 10, message = "单位10字以内")
    @NotBlank(message = "商品单位不能为空")
    @ApiModelProperty(value = "商品或服务的单位",required = true)
    private String unit;

    @NotNull(message = "商品单价不能为空")
    @ApiModelProperty(value = "商品或服务的单价（不含税）",required = true)
    private BigDecimal unitPrice;

    @NotNull(message = "商品税率不能为空")
    @ApiModelProperty(value = "商品或服务的税率（如8%或10%）",required = true)
    private Integer taxRate;

    @NotNull(message = "商品总金额不能为空")
    @ApiModelProperty(value = "商品或服务的总金额（不含税）",required = true)
    private BigDecimal amount;

    @NotNull(message = "税率类型标识不能为空")
    @ApiModelProperty(value = "税率类型：0-一般税率（10%），1-轻减税率（8%）",required = true)
    private Integer taxType;

    @NotNull(message = "商品或服务的提供日期不能为空")
    @ApiModelProperty(value = "商品或服务的日期",required = true)
    private LocalDate date;
}
