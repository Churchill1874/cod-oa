package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("training")
public class Training extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -5495643555114588149L;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("类型 备注: 前端传入什么这里寸什么. 都是中文值, 类型有 规章制度/业务文档/技术相关/学习视频/公告信息")
    private String type;

    @ApiModelProperty("系统管理员账号")
    private String systemClientAccount;

}
