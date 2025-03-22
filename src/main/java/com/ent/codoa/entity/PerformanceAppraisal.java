package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("performance_appraisal")
public class PerformanceAppraisal extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 1620271510682981619L;

    @ApiModelProperty("考核的月份 格式 2020-01 年-月 没有日")
    private String date;

    @ApiModelProperty("员工账号")
    private String account;

    @ApiModelProperty("评价结果 A,B,C,D")
    private String level;

    @ApiModelProperty("评价内容存json字符串")
    private String appraisal;

}
