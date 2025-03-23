package com.ent.codoa.pojo.resp.invoice;

import com.ent.codoa.entity.InvoiceItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = -7491214358525593596L;


    @ApiModelProperty(value = "发票的id")
    private Long id;

    @ApiModelProperty(value = "发票的发行日期")
    private LocalDate issueDate;


    @ApiModelProperty(value = "发票的请求编号")
    private String invoiceNumber;


    @ApiModelProperty(value = "企业的税务登记编号")
    private String registrationNumber;


    @ApiModelProperty(value = "供应商名称")
    private String supplierName;


    @ApiModelProperty(value = "供应商的地址")
    private String supplierAddress;


    @ApiModelProperty(value = "供应商的电话号码")
    private String supplierPhone;


    @ApiModelProperty(value = "供应商邮箱")
    private String supplierEmail;


    @ApiModelProperty(value = "客户名称")
    private String customerName;


    @ApiModelProperty(value = "客户地址")
    private String customerAddress;


    @ApiModelProperty(value = "上次请求的金额（含税）")
    private BigDecimal previousAmount;

    @ApiModelProperty(value = "已支付的金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "小计金额(不含税)")
    private BigDecimal subTotal;

    @ApiModelProperty(value = "消费税")
    private BigDecimal taxAmount;

    @ApiModelProperty(value = "总计金额")
    private BigDecimal subTaxTotal;

    @ApiModelProperty(value = "结转金额")
    private BigDecimal carryOverAmount;


    @ApiModelProperty(value = "本次请求的总金额（含税）")
    private BigDecimal totalAmount;


    @ApiModelProperty(value = "收款银行名称")
    private String bankName;


    @ApiModelProperty(value = "银行支行名称")
    private String branchName;


    @ApiModelProperty(value = "银行账户号码")
    private String accountNumber;


    @ApiModelProperty(value = "账户持有人名称")
    private String accountHolder;


    @ApiModelProperty(value = "付款截止日期")
    private LocalDate paymentDueDate;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "商品明细")
    private List<InvoiceItem> items;
}
