package com.ent.codoa.pojo.req.customerworkorder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CustomerWorkOrderAdd implements Serializable {
    private static final long serialVersionUID = -220289486574387025L;

    @NotBlank(message = "标题不能为空")
    @Length(max = 50, message = "标题长度不能超过50")
    @ApiModelProperty(value = "工单标题",required = true)
    private String title;


    @NotBlank(message = "问题描述不能为空")
    @Length(max = 255, message = "问题描述不能超过255")
    @ApiModelProperty(value = "问题描述",required = true)
    private String problem;

}
