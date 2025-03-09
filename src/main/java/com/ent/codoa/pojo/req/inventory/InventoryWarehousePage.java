package com.ent.codoa.pojo.req.inventory;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
public class InventoryWarehousePage extends PageBase implements Serializable {
    private static final long serialVersionUID = -390929984207492126L;


    @Positive(message = "仓库id数值错误")
    @NotNull(message = "仓库id不能为空")
    @ApiModelProperty(value = "固定传入，不做筛选条件，仓库id",required = true)
    private Long warehouseId;



}
