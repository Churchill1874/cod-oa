package com.ent.codoa.pojo.req.customerworkorder;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerWorkOrderPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -610454270853530940L;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("标题")
    private String title;

}
