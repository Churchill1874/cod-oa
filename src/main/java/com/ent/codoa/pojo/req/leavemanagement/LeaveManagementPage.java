package com.ent.codoa.pojo.req.leavemanagement;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LeaveManagementPage extends PageBase implements Serializable {
    private static final long serialVersionUID = 6682442576308557884L;

    @ApiModelProperty("员工账号")
    private String account;

}
