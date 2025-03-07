package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.InventoryStatusEnum;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@TableName("inventory")
@Data
public class Inventory extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -8189625670883620225L;


    @ApiModelProperty("商品id")
    private Long productId;


    @ApiModelProperty("仓库id")
    private Long warehouseId;


    @ApiModelProperty("供应商")
    private String supplier;


    @ApiModelProperty("批次号")
    private String batchNumber;


    @ApiModelProperty("库存数量")
    private Integer originalQuantity;


    @ApiModelProperty("现有库存")
    private Integer quantity;


    @ApiModelProperty("生产日期")
    private LocalDate productionDate;


    @ApiModelProperty("过期时间")
    private LocalDate expirationDate;


    @ApiModelProperty("库存状态")
    private InventoryStatusEnum status;

    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;


}
