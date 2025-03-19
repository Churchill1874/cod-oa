package com.ent.codoa.pojo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class MonthBase implements Serializable {
    private static final long serialVersionUID = 5116395734950816702L;

    @NotBlank(message = "月份不能为空")
    @ApiModelProperty(value = "指定月份 年月 2025-01",required = true)
    private String month;
}
