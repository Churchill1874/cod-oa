package com.ent.codoa.pojo.resp.token;

import com.ent.codoa.common.constant.enums.AdminRoleEnum;
import com.ent.codoa.common.constant.enums.SystemMenuEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LoginToken implements Serializable {
    private static final long serialVersionUID = 5130986957455861652L;

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("token令牌")
    private String tokenId;
    @ApiModelProperty("是否系统用户 true是 false系统用户的客户或职员")
    private Boolean isSystemClient;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("管理员角色 只有在 isSystemClient为true的时候才有这个值")
    private AdminRoleEnum role;
    @ApiModelProperty("登录时间")
    private LocalDateTime loginTime;
    @ApiModelProperty("拥有的系统菜单列表")
    private List<SystemMenuEnum> menuList;

}
