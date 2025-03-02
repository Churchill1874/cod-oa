package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("business_client")
public class BusinessClient extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 1707068221541537521L;

    @TableField("system_client_account")
    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;

    @TableField("name")
    @ApiModelProperty("业务用户名称")
    private String name;

    @TableField("account")
    @ApiModelProperty("业务用户账号")
    private String account;

    @TableField("password")
    @ApiModelProperty("密码")
    private String password;

    @TableField("last_login_time")
    @ApiModelProperty("最后一次登录账号")
    private LocalDateTime lastLoginTime;

    @TableField("salt")
    @ApiModelProperty("盐值")
    private String salt;

    @TableField("status")
    @ApiModelProperty("状态")
    private UserStatusEnum status;

}
