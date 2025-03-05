package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.ComplaintStatusEnum;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("complaint")
public class Complaint extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 1840748948153836426L;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("状态")
    private ComplaintStatusEnum status;


}
