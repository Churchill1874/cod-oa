package com.ent.codoa.pojo.req.customercontract;

import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class CustomerContractUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = 2699150199470425808L;

    @Length(max = 30, message = "客户名字不能超过30位")
    @NotBlank(message = "客户名字不能为空")
    @ApiModelProperty(value = "客户名字",required = true)
    private String name;

    @Length(max = 30, message = "关联账号不能超过30位")
    @ApiModelProperty("关联账号")
    private String account;

    @ApiModelProperty("合同字符串")
    private String contract;

    @Length(max = 100,message = "备注不能超过100位")
    @ApiModelProperty("备注")
    private String remark;

}
