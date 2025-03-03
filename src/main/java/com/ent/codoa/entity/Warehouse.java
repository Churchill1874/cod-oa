package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("warehouse")
public class Warehouse extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 3430791083072864624L;

    @TableField("name")
    @ApiModelProperty("仓库名称")
    private String name;

    @TableField("address")
    @ApiModelProperty("仓库地址")
    private String address;

    @TableField("system_client_account")
    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;

}
