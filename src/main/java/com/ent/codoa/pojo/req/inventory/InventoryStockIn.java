package com.ent.codoa.pojo.req.inventory;

import com.ent.codoa.common.constant.enums.InventoryStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class InventoryStockIn implements Serializable {
    private static final long serialVersionUID = 1131377932027089172L;

    @NotBlank(message = "商品id不能为空")
    @ApiModelProperty(value = "商品id",required = true)
    private Long productId;

    @NotBlank(message = "仓库id不能为空")
    @ApiModelProperty(value = "仓库id",required = true)
    private Long warehouseId;

    @NotBlank(message = "批次号不能为空")
    @ApiModelProperty(value = "批次号",required = true)
    private String batchNumber;

    @NotBlank(message = "供应商不能为空")
    @ApiModelProperty(value = "供应商",required = true)
    private String supplier;

    @NotBlank(message = "入库数量不能为空")
    @ApiModelProperty(value = "入库数量",required = true)
    private Integer originalQuantity;

    @NotBlank(message = "生产日期不能为空")
    @ApiModelProperty(value = "生产日期",required = true)
    private LocalDate productionDate;

    @NotBlank(message = "过期时间不能为空")
    @ApiModelProperty(value = "过期时间",required = true)
    private LocalDate expirationDate;
}
