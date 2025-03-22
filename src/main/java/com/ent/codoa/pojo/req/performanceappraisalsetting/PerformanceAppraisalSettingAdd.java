package com.ent.codoa.pojo.req.performanceappraisalsetting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PerformanceAppraisalSettingAdd implements Serializable {
    private static final long serialVersionUID = 3576239658892777141L;

    @NotBlank(message = "评价关键字不能为空")
    @ApiModelProperty(value = "评价关键字",required = true)
    private String appraisalKey;

}
