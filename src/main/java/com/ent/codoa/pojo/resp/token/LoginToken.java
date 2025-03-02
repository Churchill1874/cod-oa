package com.ent.codoa.pojo.resp.token;

import com.ent.codoa.common.constant.enums.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LoginToken implements Serializable {
    private static final long serialVersionUID = 5130986957455861652L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("token令牌")
    private String tokenId;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("角色")
    private RoleEnum role;

    @ApiModelProperty("登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty("该登陆人 所属系统用户的账号")
    private String systemClientAccount;

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
