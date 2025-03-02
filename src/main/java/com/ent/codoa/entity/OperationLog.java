package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("operation_log")
public class OperationLog extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 62515240801594501L;

    @ApiModelProperty("描述")
    private String introduce;

    @ApiModelProperty("操作的菜单")
    private String menu;

    @ApiModelProperty("操作账号")
    private String account;

    @ApiModelProperty("所属系统用户账号 只存系统用户账号")
    private String systemClientAccount;

}
