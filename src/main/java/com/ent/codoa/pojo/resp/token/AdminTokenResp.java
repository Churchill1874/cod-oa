package com.ent.codoa.pojo.resp.token;

import com.ent.codoa.common.constant.enums.AdminRoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AdminTokenResp implements Serializable {
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
    private AdminRoleEnum role;
    @ApiModelProperty("登录时间")
    private LocalDateTime loginTime;

}
