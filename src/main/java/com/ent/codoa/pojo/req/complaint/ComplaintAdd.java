package com.ent.codoa.pojo.req.complaint;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ComplaintAdd implements Serializable {
    private static final long serialVersionUID = -636496237810684485L;

    @NotBlank(message = "请输入投诉内容")
    @ApiModelProperty("投诉内容")
    private String content;

}
