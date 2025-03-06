package com.ent.codoa.pojo.req.customerworkorderdialogue;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CustomerWorkOrderDialoguePage extends PageBase implements Serializable {
    private static final long serialVersionUID = 2414861599333337286L;

    @NotNull(message = "工单id不能为空")
    @ApiModelProperty(value = "工单id", required = true)
    private Long workOrderId;

}
