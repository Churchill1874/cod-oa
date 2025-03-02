package com.ent.codoa.pojo.req.businessclient;

import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BusinessClientStatusUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -5585245265786326825L;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty("状态")
    private UserStatusEnum status;

}
