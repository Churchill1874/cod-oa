package com.ent.codoa.pojo.req.customer;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerPage extends PageBase implements Serializable {
    private static final long serialVersionUID = 157872978319727673L;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("企业名称")
    private String enterpriseName;

}
