package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.entity.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("customer")
public class Customer extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -5425796993550068676L;

    @ApiModelProperty("客户名称")
    private String name;

    @ApiModelProperty("账号")
    private String account;

    @JsonIgnore
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("状态")
    private UserStatusEnum status;

    @JsonIgnore
    @ApiModelProperty("加密盐值")
    private String salt;

    @ApiModelProperty("最后一次登陆时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("企业名称")
    private String enterpriseName;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("联系方式描述")
    private String contact;

    @ApiModelProperty("行业描述")
    private String industry;

    @ApiModelProperty("规模描述")
    private String scale;

    @ApiModelProperty("介绍信息")
    private String introduce;

    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;

}
