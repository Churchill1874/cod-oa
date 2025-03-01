package com.ent.codoa.pojo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BooleanBase implements Serializable {
    private static final long serialVersionUID = -710621387885755428L;

    @ApiModelProperty("结果值")
    private boolean value;

}
