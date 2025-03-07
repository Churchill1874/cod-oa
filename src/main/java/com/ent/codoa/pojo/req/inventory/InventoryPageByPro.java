package com.ent.codoa.pojo.req.inventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class InventoryPageByPro implements Serializable {

    private static final long serialVersionUID = -8493877792699846294L;

    @NotBlank(message = "商品id不能为空")
    @ApiModelProperty(value = "商品id",required = true)
    private Long productId;

    @NotBlank(message = "仓库id不能为空")
    @ApiModelProperty(value = "仓库id",required = true)
    private Long warehouseId;
}
