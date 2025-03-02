package com.ent.codoa.pojo.req.staff;

import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class StaffStatusUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -5958733033747114324L;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态", required = true)
    private UserStatusEnum status;

}
