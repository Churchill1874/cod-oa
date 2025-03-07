package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@TableName("product")
@Data
public class Product extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -8822791587872262493L;

    @ApiModelProperty("商品名称")
    private String name;


    @ApiModelProperty("商品描述")
    private String description;


    @ApiModelProperty("商品类别")
    private String category;


    @ApiModelProperty("商品单位")
    private String unit;


    @ApiModelProperty("仓库id")
    private Long warehouseId;


    @ApiModelProperty("预警数量")
    private Integer warningQuantity;


    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;
}
