package com.ent.codoa.pojo.req.inventory;

import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class InventoryStockOut  implements Serializable {
    private static final long serialVersionUID = 669676907819174415L;


    @NotBlank(message = "商品id不能为空")
    @ApiModelProperty(value = "商品id",required = true)
    private Long productId;

    @NotBlank(message = "仓库id不能为空")
    @ApiModelProperty(value = "仓库id",required = true)
    private Long warehouseId;

    @NotBlank(message = "出库数量不能为空")
    @ApiModelProperty(value = "出库数量",required = true)
    private Integer quantity;


    @NotBlank(message = "批次号不能为空")
    @ApiModelProperty(value = "批次号",required = true)
    private String batchNumber;
}
