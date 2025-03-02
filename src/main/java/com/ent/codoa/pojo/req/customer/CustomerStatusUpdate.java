package com.ent.codoa.pojo.req.customer;

import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerStatusUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = 3974715109751321832L;

    @ApiModelProperty(value = "状态", required = true)
    private UserStatusEnum status;

}
