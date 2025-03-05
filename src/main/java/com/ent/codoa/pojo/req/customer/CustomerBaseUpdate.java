package com.ent.codoa.pojo.req.customer;

import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CustomerBaseUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -8187928191343598367L;

    @NotBlank(message = "客户名称不能为空")
    @Length(max = 30, message = "客户名称不能超过30位")
    @ApiModelProperty(value = "客户名称", required = true)
    private String name;

    @Length(max = 30, message = "企业名称不能超过30位")
    @ApiModelProperty("企业名称")
    private String enterpriseName;

    @Length(max = 30, message = "地址不能超过100位")
    @ApiModelProperty("地址")
    private String address;

    @Length(max = 30, message = "联系方式描述不能超过100位")
    @ApiModelProperty("联系方式描述")
    private String contact;

    @Length(max = 30, message = "行业描述不能超过30位")
    @ApiModelProperty("行业描述")
    private String industry;

    @Length(max = 30, message = "规模描述不能超过20位")
    @ApiModelProperty("规模描述")
    private String scale;

    @Length(max = 30, message = "介绍信息不能超过255位")
    @ApiModelProperty("介绍信息")
    private String introduce;

}
