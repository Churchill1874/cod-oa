package com.ent.codoa.pojo.req.systemclient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AdminLogin implements Serializable {

    private static final long serialVersionUID = 4559462008185124843L;

    @NotNull(message = "账号不能为空")
    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank(message = "必须选择语言")
    @ApiModelProperty(value = "语言", required = true)
    private String lang;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String verificationCode;


}
