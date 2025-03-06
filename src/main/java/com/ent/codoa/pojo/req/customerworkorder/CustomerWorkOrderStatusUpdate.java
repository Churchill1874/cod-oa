package com.ent.codoa.pojo.req.customerworkorder;

import com.ent.codoa.common.constant.enums.CustomerWorkOrderStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CustomerWorkOrderStatusUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = 6463094572827676836L;

    @NotNull(message = "工单状态不能为空")
    @ApiModelProperty(value = "工单状态" ,required = true)
    private CustomerWorkOrderStatusEnum status;

}
