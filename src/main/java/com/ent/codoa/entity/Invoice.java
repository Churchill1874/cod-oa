package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@TableName("invoice")
public class Invoice extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -211800933953975930L;
    @ApiModelProperty("发票的发行日期")
    private LocalDate issueDate;

    @ApiModelProperty("发票的请求编号")
    private String invoiceNumber;

    @ApiModelProperty("企业的税务登记编号")
    private String registrationNumber;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应商的地址")
    private String supplierAddress;

    @ApiModelProperty("供应商的电话号码")
    private String supplierPhone;

    @ApiModelProperty("供应商邮箱")
    private String supplierEmail;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("客户地址")
    private String customerAddress;

    @ApiModelProperty("上次请求的金额（含税）")
    private BigDecimal previousAmount;

    @ApiModelProperty("已支付的金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty("结转金额")
    private BigDecimal carryOverAmount;

    @ApiModelProperty("小计金额")
    private BigDecimal subTotal;

    @ApiModelProperty("消费税")
    private BigDecimal taxAmount;

    @ApiModelProperty("总计金额")
    private BigDecimal subTaxTotal;

    @ApiModelProperty("本次请求的总金额（含税）")
    private BigDecimal totalAmount;

    @ApiModelProperty("收款银行名称")
    private String bankName;

    @ApiModelProperty("银行支行名称")
    private String branchName;

    @ApiModelProperty("银行账户号码")
    private String accountNumber;

    @ApiModelProperty("账户持有人名称")
    private String accountHolder;

    @ApiModelProperty("付款截止日期")
    private LocalDate paymentDueDate;

    @ApiModelProperty("备注信息")
    private String remarks;

    @ApiModelProperty("发票图片的Base64数据")
    private String image;

    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;


    // 商品明细（不存储到数据库，仅用于业务逻辑）
    @TableField(exist = false)
    private List<InvoiceItem> items;

}

