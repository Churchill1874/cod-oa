package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("performance_appraisal_setting")
public class PerformanceAppraisalSetting extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -5195621259830934010L;

    @ApiModelProperty("评价关键字")
    private String appraisalKey;
    @ApiModelProperty("系统管理员账号")
    private String systemClientAccount;

}
