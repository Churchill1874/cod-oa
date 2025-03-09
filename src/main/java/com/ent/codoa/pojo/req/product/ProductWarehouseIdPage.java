package com.ent.codoa.pojo.req.product;


import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
public class ProductWarehouseIdPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -9190110390231868976L;

    @Positive(message = "仓库id数值错误")
    @NotNull(message = "仓库id不能为空")
    @ApiModelProperty(value = "固定传入，不做筛选条件，仓库id",required = true)
    private Long warehouseId;
}
