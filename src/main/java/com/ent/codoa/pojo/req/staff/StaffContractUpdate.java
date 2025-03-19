package com.ent.codoa.pojo.req.staff;

import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class StaffContractUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -7924990492197174468L;

    @NotBlank(message = "合同不能为空")
    @ApiModelProperty(value = "合同的图片base64值",required = true)
    private String contract;

}
