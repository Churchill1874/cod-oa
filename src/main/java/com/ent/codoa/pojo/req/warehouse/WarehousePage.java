package com.ent.codoa.pojo.req.warehouse;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WarehousePage extends PageBase implements Serializable {
    private static final long serialVersionUID = 8171346726766703794L;

    @ApiModelProperty(value = "仓库名称")
    private String name;
}
