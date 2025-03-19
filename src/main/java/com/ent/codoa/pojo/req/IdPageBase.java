package com.ent.codoa.pojo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
public class IdPageBase extends PageBase implements Serializable {
    private static final long serialVersionUID = -2342662527257905980L;
    @Positive(message = "数据有误")
    @NotNull(message = "id不能为空")
    @ApiModelProperty("根据id")
    private Long id;
}
