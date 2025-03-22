package com.ent.codoa.pojo.req.leavemanagement;

import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LeaveManagementApproval extends IdBase implements Serializable {
    private static final long serialVersionUID = -7162416167215068307L;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty("状态 待处理 / 通过 / 拒绝")
    private String status;

}
