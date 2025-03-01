package com.ent.codoa.pojo.req.systemclient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SystemClientAdd implements Serializable {
    private static final long serialVersionUID = -384677904217582754L;

    @NotBlank(message = "账号不能为空")
    @Length(max = 30, message = "请输入30位以内长度的账号")
    @ApiModelProperty(value = "账号 30位长度",required = true)
    private String account;

    @NotBlank(message = "名称不能为空")
    @Length(max = 30, message = "请输入30位以内的名称")
    @ApiModelProperty(value = "名称 30位长度",required = true)
    private String name;

    @NotBlank(message = "密码不能为空")
    @Length(max = 20, message = "请输入20位以内的密码")
    @ApiModelProperty(value = "密码 20位长度",required = true)
    private String password;

    @NotBlank(message = "描述不能为空")
    @Length(max = 255, message = "请输入255位以内的描述")
    @ApiModelProperty(value = "描述 255位长度", required = true)
    private String introduce;

    @NotNull(message = "到期时间不能为空")
    @ApiModelProperty(value = "到期时间 精确到日就可以 例2020-10-1 不用时分秒", required = true)
    private LocalDate expiredTime;


}
