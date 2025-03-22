package com.ent.codoa.pojo.req.performanceappraisal;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PerformanceAppraisalPage extends PageBase implements Serializable {
    private static final long serialVersionUID = 147733541392464768L;

    @ApiModelProperty("账号")
    private String account;

}
