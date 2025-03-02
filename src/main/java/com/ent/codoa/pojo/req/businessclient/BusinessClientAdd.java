package com.ent.codoa.pojo.req.businessclient;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ent.codoa.common.constant.enums.UserStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BusinessClientAdd implements Serializable {
    private static final long serialVersionUID = -5323621288103708919L;

    @NotBlank(message = "业务用户名称不能为空")
    @Length(max = 30, message = "名称长度不能超过30位")
    @ApiModelProperty("业务用户名称")
    private String name;

    @NotBlank(message = "业务用户账号不能为空")
    @Length(max = 30, message = "业务用户账号长度不能超过30位")
    @ApiModelProperty("业务用户账号")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Length(max = 20, message = "密码长度不能超过20位")
    @ApiModelProperty("密码")
    private String password;

}
