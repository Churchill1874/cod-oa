package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.CustomerWorkOrderStatusEnum;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("customer_work_order")
public class CustomerWorkOrder extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 1695601535929467810L;

    @ApiModelProperty("提起工单账号")
    private String account;

    @ApiModelProperty("工单标题")
    private String title;

    @ApiModelProperty("问题描述")
    private String problem;

    @ApiModelProperty("状态")
    private CustomerWorkOrderStatusEnum status;

    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;

}
