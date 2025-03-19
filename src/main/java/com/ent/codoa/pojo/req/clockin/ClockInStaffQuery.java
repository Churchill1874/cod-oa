package com.ent.codoa.pojo.req.clockin;

import com.ent.codoa.pojo.req.MonthBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ClockInStaffQuery extends MonthBase implements Serializable {
    private static final long serialVersionUID = -4812511151769471211L;

    @NotBlank(message = "请指定账号")
    @ApiModelProperty("账号")
    private String account;


}
