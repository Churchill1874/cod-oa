package com.ent.codoa.pojo.resp.training;

import com.ent.codoa.entity.Training;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainingVO implements Serializable {
    private static final long serialVersionUID = -4230198423260931702L;

    @ApiModelProperty("规章制度")
    private List<Training> rulesList;

    @ApiModelProperty("业务文档")
    private List<Training> businessList;

    @ApiModelProperty("技术相关")
    private List<Training> technologyList;

    @ApiModelProperty("学习视频链接")
    private List<Training> videoList;

    @ApiModelProperty("公告信息")
    private List<Training> announcementList;

}
