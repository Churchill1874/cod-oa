package com.ent.codoa.pojo.req.staff;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StaffPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -3996942822475651191L;

    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("手机")
    private String phone;

}
