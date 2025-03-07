package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.OperationTypeEnum;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("stock_operation")
@Data
public class StockOperation extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -3132468687468607698L;


    @ApiModelProperty("商品id")
    private Long productId;


    @ApiModelProperty("仓库id")
    private Long warehouseId;


    @ApiModelProperty("供应商")
    private String supplier;


    @ApiModelProperty("库存数量")
    private Integer quantity;


    @ApiModelProperty("批次号")
    private String batchNumber;

    @ApiModelProperty("操作类型")
    private OperationTypeEnum operationType;

    @ApiModelProperty("生产日期")
    private LocalDate productionDate;


    @ApiModelProperty("过期时间")
    private LocalDate expirationDate;


    @ApiModelProperty("操作时间")
    private LocalDateTime operationTime;


    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;
}
