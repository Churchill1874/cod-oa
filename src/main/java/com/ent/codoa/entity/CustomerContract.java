package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("customer_contract")
public class CustomerContract extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 632590389758215190L;

    @ApiModelProperty("客户名字")
    private String name;
    @ApiModelProperty("关联账号")
    private String account;
    @ApiModelProperty("合同字符串")
    private String contract;
    @ApiModelProperty("备注 比如什么什么的合同")
    private String remark;

}
