package com.ent.codoa.pojo.req.customerworkorderdialogue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CustomerWorkOrderDialogueAdd implements Serializable {
    private static final long serialVersionUID = 3614617199090392348L;

    @NotNull(message = "工单id不能为空")
    @ApiModelProperty(value = "工单id",required = true)
    private Long workOrderId;

    @Length(max = 500, message = "回复内容不能超过1000字")
    @NotBlank(message = "回复内容不能为空")
    @ApiModelProperty(value = "工单回复内容",required = true)
    private String content;

}
