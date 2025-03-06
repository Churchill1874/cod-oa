package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("customer_work_order_dialogue")
public class CustomerWorkOrderDialogue extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -3683180206184123976L;

    @ApiModelProperty("工单id")
    private Long workOrderId;

    @ApiModelProperty("该工单回复人的账号 要么是客户 要么是系统用户")
    private String account;

    @ApiModelProperty("工单回复内容")
    private String content;

    @ApiModelProperty("是否客户的对话记录")
    private Boolean isCustomer;

}
