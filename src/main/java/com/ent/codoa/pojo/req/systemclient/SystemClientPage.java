package com.ent.codoa.pojo.req.systemclient;

import com.ent.codoa.common.constant.enums.SystemClientStatusEnum;
import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SystemClientPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -1775645682659036402L;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态")
    private SystemClientStatusEnum status;

}
