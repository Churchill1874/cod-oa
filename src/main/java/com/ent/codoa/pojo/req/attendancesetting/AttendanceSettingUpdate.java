package com.ent.codoa.pojo.req.attendancesetting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AttendanceSettingUpdate implements Serializable {
    private static final long serialVersionUID = 4831741653309975912L;

    @NotBlank(message = "上班时间不能为空")
    @ApiModelProperty(value = "上班时间 ,注意 没有年月日 就是时分秒 比如 09:00 冒号不是中文冒号", required = true)
    private String startWork;

    @NotBlank(message = "下班时间不能为空")
    @ApiModelProperty(value = "下班时间 ,注意 没有年月日 就是时分秒 比如 09:00 冒号不是中文冒号", required = true)
    private String endWork;

}
