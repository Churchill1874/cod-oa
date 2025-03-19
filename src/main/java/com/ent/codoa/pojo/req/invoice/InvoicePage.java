package com.ent.codoa.pojo.req.invoice;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InvoicePage extends PageBase implements Serializable {
    private static final long serialVersionUID = 7681729190432334066L;

    @ApiModelProperty(value = "发票的请求编号")
    private String invoiceNumber;


    @ApiModelProperty(value = "企业的税务登记编号")
    private String registrationNumber;


    @ApiModelProperty(value = "供应商名称")
    private String supplierName;


    @ApiModelProperty(value = "客户名称")
    private String customerName;
}
