package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.RoleEnum;
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

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("状态")
    private SystemClientStatusEnum status;

    @ApiModelProperty("描述")
    private String introduce;

    @ApiModelProperty("到期时间")
    private LocalDate expiredTime;

    @ApiModelProperty("最后一次登陆时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("角色")
    private RoleEnum role;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("客户管理权限")
    private Boolean customerMenu;

    @ApiModelProperty("库存管理权限")
    private Boolean inventoryMenu;

    @ApiModelProperty("人事管理权限")
    private Boolean hrMenu;

    @ApiModelProperty("支付管理权限")
    private Boolean paymentMenu;

    @ApiModelProperty("平台管理菜单")
    private Boolean platformMenu;


}
