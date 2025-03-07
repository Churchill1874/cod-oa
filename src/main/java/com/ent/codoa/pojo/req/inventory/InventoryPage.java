package com.ent.codoa.pojo.req.inventory;

import com.ent.codoa.common.constant.enums.InventoryStatusEnum;
import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class InventoryPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -390929984207492126L;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @NotBlank(message = "仓库id不能为空")
    @ApiModelProperty(value = "固定传入，不做筛选条件，仓库id",required = true)
    private Long warehouseId;

    @ApiModelProperty(value = "库存状态",required = true)
    private InventoryStatusEnum status;


}
