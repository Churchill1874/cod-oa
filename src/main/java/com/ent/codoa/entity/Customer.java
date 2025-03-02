package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("customer")
public class Customer extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -5425796993550068676L;

    @TableField("name")
    @ApiModelProperty("客户名称")
    private String name;

    @TableField("account")
    @ApiModelProperty("账号")
    private String account;

    @TableField("password")
    @ApiModelProperty("密码")
    private String password;

    @TableField("salt")
    @ApiModelProperty("加密盐值")
    private String salt;

    @TableField("last_login_time")
    @ApiModelProperty("最后一次登陆时间")
    private LocalDateTime lastLoginTime;

    @TableField("enterprise_name")
    @ApiModelProperty("企业名称")
    private String enterpriseName;

    @TableField("address")
    @ApiModelProperty("地址")
    private String address;

    @TableField("contact")
    @ApiModelProperty("联系方式描述")
    private String contact;

    @TableField("industry")
    @ApiModelProperty("行业描述")
    private String industry;

    @TableField("scale")
    @ApiModelProperty("规模描述")
    private String scale;

    @TableField("introduce")
    @ApiModelProperty("介绍信息")
    private String introduce;

}
