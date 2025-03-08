package com.ent.codoa.pojo.req.product;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
public class ProductPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -5135143571625619608L;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品类别")
    private String category;

    @Positive(message = "仓库id数值错误")
    @NotNull(message = "仓房id不能为空")
    @ApiModelProperty(value = "仓库id,只查这个仓库的商品，不做过滤条件",required = true)
    private Long warehouseId;

}
