package com.ent.codoa.pojo.req.invoice;

import com.ent.codoa.pojo.req.invoiceitem.InvoiceItemAdd;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceAdd  implements Serializable {


    private static final long serialVersionUID = 5484083626637683538L;
    @NotNull(message = "发票的发行日期不能为空")
    @ApiModelProperty(value = "发票的发行日期",required = true)
    private LocalDate issueDate;

    @NotBlank(message = "发票的请求编号不能为空")
    @Length(max = 50, message = "发票的请求编号必须50字以内")
    @Pattern(regexp = "^\\d{6}$", message = "发票的请求编号必须为6位数字")
    @ApiModelProperty(value = "发票的请求编号",required = true)
    private String invoiceNumber;

    @NotBlank(message = "企业的税务登记编号不能为空")
    @Length(max = 50, message = "企业的税务登记编号必须50字以内")
    @Pattern(regexp = "^T\\d{12}$", message = "登録番号格式无效,必须为T+12位数字")
    @ApiModelProperty(value = "企业的税务登记编号",required = true)
    private String registrationNumber;


    @Length(max = 50, message = "供应商名称必须50字以内")
    @ApiModelProperty(value = "供应商名称")
    private String supplierName="";


    @Length(max = 100, message = "供应商地址必须100字以内")
    @ApiModelProperty(value = "供应商的地址")
    private String supplierAddress="";


    @Length(max = 20, message = "供应商电话必须20字以内")
    @ApiModelProperty(value = "供应商的电话号码")
    private String supplierPhone="";


    @Length(max = 30, message = "供应商邮箱必须30字以内")
    @ApiModelProperty(value = "供应商邮箱")
    private String supplierEmail="";

    @NotBlank(message = "客户名称不能为空")
    @Length(max = 50, message = "客户名称必须50字以内")
    @ApiModelProperty(value = "客户名称",required = true)
    private String customerName;

    @NotBlank(message = "客户地址不能为空")
    @Length(max = 100, message = "客户地址必须100字以内")
    @ApiModelProperty(value = "客户地址",required = true)
    private String customerAddress;

    @ApiModelProperty(value = "上次请求的金额（含税）")
    private BigDecimal previousAmount=BigDecimal.ZERO;

    @NotNull(message = "已支付的金额不能为空")
    @ApiModelProperty(value = "已支付的金额",required = true)
    private BigDecimal paymentAmount;

    @NotNull(message = "结转金额不能为空")
    @ApiModelProperty(value = "结转金额",required = true)
    private BigDecimal carryOverAmount;

//    @NotNull(message = "本次请求的总金额（含税）不能为空")
//    @ApiModelProperty("本次请求的总金额（含税）")
//    private BigDecimal totalAmount;

    @NotBlank(message = "收款银行不能为空")
    @Length(max = 50, message = "供应商地址必须50字以内")
    @ApiModelProperty(value = "收款银行名称",required = true)
    private String bankName;

    @NotBlank(message = "银行支行不能为空")
    @Length(max = 50, message = "供应商地址必须50字以内")
    @ApiModelProperty(value = "银行支行名称",required = true)
    private String branchName;

    @NotBlank(message = "银行账户号码不能为空")
    @Length(max = 50, message = "供应商地址必须50字以内")
    @ApiModelProperty(value = "银行账户号码",required = true)
    private String accountNumber;

    @NotBlank(message = "账户持有人名称不能为空")
    @Length(max = 100, message = "供应商地址必须100字以内")
    @ApiModelProperty(value = "账户持有人名称",required = true)
    private String accountHolder;

    @NotNull(message = "付款截止日期不能为空")
    @ApiModelProperty(value = "付款截止日期",required = true)
    private LocalDate paymentDueDate;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "商品明细")
    private List<InvoiceItemAdd> items;
}
