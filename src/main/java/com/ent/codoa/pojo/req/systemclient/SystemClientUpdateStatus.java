package com.ent.codoa.pojo.req.systemclient;

import com.ent.codoa.common.constant.enums.SystemClientStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SystemClientUpdateStatus extends IdBase implements Serializable {
    private static final long serialVersionUID = -3893837693284125951L;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty("系统用户状态")
    private SystemClientStatusEnum status;

}
