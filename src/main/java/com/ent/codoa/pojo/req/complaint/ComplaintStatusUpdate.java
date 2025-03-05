package com.ent.codoa.pojo.req.complaint;

import com.ent.codoa.common.constant.enums.ComplaintStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ComplaintStatusUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -2210616216144862518L;

    @NotNull(message = "投诉状态不能为空")
    @ApiModelProperty("投诉状态")
    private ComplaintStatusEnum status;


}
