package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("tax_rate")
public class TaxRate extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -7350601045715842789L;

    @ApiModelProperty("上限")
    private BigDecimal upLimit;
    @ApiModelProperty("下限")
    private BigDecimal downLimit;
    @ApiModelProperty("比例")
    private Integer rate;

}
