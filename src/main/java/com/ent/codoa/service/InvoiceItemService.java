package com.ent.codoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.InvoiceItem;
import com.ent.codoa.pojo.req.invoiceitem.InvoiceItemAmount;
import com.ent.codoa.pojo.req.invoiceitem.InvoiceItemTax;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceItemService extends IService<InvoiceItem> {
    /**
     * 新增发票的商品明细
     * @param dto
     */
    void add(InvoiceItem dto);

    /**
     * 根据发票Id获取商品明细
     * @param id
     * @return
     */
    List<InvoiceItem> getItems(Long id);

    /**
     * 计算单一商品或服务明细的总金额
     * @param dto
     * @return
     */
    BigDecimal calculateSubAmount(InvoiceItemAmount dto);

    /**
     * 计算单一商品或服务明细的总金额
     * @param dto
     * @return
     */
    BigDecimal calculateSubTax(InvoiceItemTax dto);

    /**
     * 根据商品或服务明细，计算该明细消费税
     * @param item
     * @return
     */


    BigDecimal calculateSubTax(InvoiceItem item);

    /**
     * 计算小计（不含税商品或服务总金额）
     * @param items
     * @return
     */
    BigDecimal calculateSubtotal(List<InvoiceItem> items);

    /**
     * 计算消费税（金額（不含税） × 税率）
     * @param items
     * @return
     */
    BigDecimal calculateTax(List<InvoiceItem> items);

    /**
     * 计算总计（小计+消费税）
     * @param subtotal
     * @param tax
     * @return
     */
    BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal tax);

    /**
     * 计算本次请求总金额（小计（不含税）+）
     * @param total
     * @param carryOverAmount
     * @return
     */
    BigDecimal calculateAllTotal(BigDecimal total,BigDecimal carryOverAmount);
}
