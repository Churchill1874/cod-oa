package com.ent.codoa.pojo.resp.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductQantityVO implements Serializable {
    private static final long serialVersionUID = -2891978191447715655L;

    @ApiModelProperty("商品id")
    private  Long id;

    @ApiModelProperty("商品名称")
    private String name;


    @ApiModelProperty("商品类别")
    private String category;


    @ApiModelProperty("单位名称")
    private String unit;


    @ApiModelProperty("仓库id")
    private Long warehouseId;


    @ApiModelProperty("预警数量")
    private Integer warningQuantity = 0;

    @ApiModelProperty("实际库存数量")
    private Integer totalQuantity = 0;
}
