package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.AdminRoleEnum;
import com.ent.codoa.common.constant.enums.SystemClientStatusEnum;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("system_client")
public class SystemClient extends BaseInfo implements Serializable {

    private static final long serialVersionUID = 5766042689756565051L;

    @TableField("account")
    @ApiModelProperty("账号")
    private String account;

    @TableField("name")
    @ApiModelProperty("名称")
    private String name;

    @TableField("password")
    @ApiModelProperty("密码")
    private String password;

    @TableField("status")
    @ApiModelProperty("状态")
    private SystemClientStatusEnum status;

    @TableField("describe")
    @ApiModelProperty("描述")
    private String describe;

    @TableField("expired_time")
    @ApiModelProperty("到期时间")
    private LocalDate expiredTime;

    @TableField("last_login_time")
    @ApiModelProperty("最后一次登陆时间")
    private LocalDateTime lastLoginTime;

    @TableField("role")
    @ApiModelProperty("角色")
    private AdminRoleEnum role;

    @TableField("salt")
    @ApiModelProperty("盐")
    private String salt;

}
