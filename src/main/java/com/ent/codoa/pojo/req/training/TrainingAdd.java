package com.ent.codoa.pojo.req.training;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class TrainingAdd implements Serializable {
    private static final long serialVersionUID = 4729428923156063989L;

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty("标题")
    private String title;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @NotBlank(message = "类型不能为空")
    @ApiModelProperty("类型有 规章制度/业务文档/技术相关/学习视频/同步信息. 所以前端下拉应该该是这些中文 备注: 前端传入什么后端存什么. 都是中文值, ")
    private String type;

}
