package com.ent.codoa.pojo.req.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class CustomerAdd implements Serializable {
    private static final long serialVersionUID = 7613895160290932212L;

    @Length(max = 30, message = "客户名称不能超过30位")
    @ApiModelProperty(value = "客户名称",required = true)
    private String name;

    @Length(max = 30, message = "账号不能超过30位")
    @ApiModelProperty(value = "账号",required = true)
    private String account;

    @Length(max = 30, message = "企业名称不能超过30位")
    @ApiModelProperty(value = "企业名称",required = true)
    private String enterpriseName;

    @Length(max = 30, message = "地址不能超过100位")
    @ApiModelProperty(value = "地址")
    private String address;

    @Length(max = 30, message = "联系方式描述不能超过100位")
    private String contact;

    @Length(max = 30, message = "行业描述不能超过30位")
    @ApiModelProperty(value = "行业描述")
    private String industry;

    @Length(max = 30, message = "规模描述不能超过20位")
    @ApiModelProperty("规模描述")
    private String scale;

    @Length(max = 30, message = "介绍信息不能超过255位")
    @ApiModelProperty("介绍信息")
    private String introduce;

}
