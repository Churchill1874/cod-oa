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

    @TableField("introduce")
    @ApiModelProperty("描述")
    private String introduce;

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

    @TableField("customer_menu")
    @ApiModelProperty("客户管理权限")
    private Boolean customerMenu;

    @TableField("inventory_menu")
    @ApiModelProperty("库存管理权限")
    private Boolean inventoryMenu;

    @TableField("hr_menu")
    @ApiModelProperty("人事管理权限")
    private Boolean hrMenu;

    @TableField("payment_menu")
    @ApiModelProperty("支付管理权限")
    private Boolean paymentMenu;

    @TableField("platform_menu")
    @ApiModelProperty("平台管理菜单")
    private Boolean platformMenu;

    @TableField("business_menu")
    @ApiModelProperty("业务用户菜单")
    private Boolean businessMenu;

}
