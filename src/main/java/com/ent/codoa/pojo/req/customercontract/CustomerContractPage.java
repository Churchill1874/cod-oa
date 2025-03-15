package com.ent.codoa.pojo.req.customercontract;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerContractPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -1304110246345394647L;

    @ApiModelProperty(value = "关联的账号")
    private String account;

    @ApiModelProperty(value = "客户名称")
    private String name;

}
