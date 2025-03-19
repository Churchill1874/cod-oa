package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("invoice_item")
@Data
public class InvoiceItem extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -2845068289309778265L;

    @ApiModelProperty("商品或服务的内容")
    private String description;

    @ApiModelProperty("商品或服务的数量")
    private Integer quantity;

    @ApiModelProperty("商品或服务的单位")
    private String unit;

    @ApiModelProperty("商品或服务的单价（不含税）")
    private BigDecimal unitPrice;

    @ApiModelProperty("商品或服务的税率（如8%或10%）")
    private Integer taxRate;

    @ApiModelProperty("商品或服务的总金额（不含税）")
    private BigDecimal amount;

    @ApiModelProperty("消费税")
    private BigDecimal taxAmount;

    @ApiModelProperty("发票id")
    private Long invoiceId;

    @ApiModelProperty("商品或服务的日期")
    private LocalDate date;

    @ApiModelProperty("税率类型：0-一般税率（10%），1-轻减税率（8%）")
    private Integer taxType;

    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;


}
