package com.ent.codoa.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.InvoiceItem;
import com.ent.codoa.mapper.InvoiceItemMapper;
import com.ent.codoa.pojo.req.invoiceitem.InvoiceItemAmount;
import com.ent.codoa.pojo.req.invoiceitem.InvoiceItemTax;
import com.ent.codoa.service.InvoiceItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceItemServiceImpl extends ServiceImpl<InvoiceItemMapper, InvoiceItem> implements InvoiceItemService {
    @Override
    public void add(InvoiceItem dto) {
        dto.setTaxAmount(calculateSubTax(dto));
        dto.setCreateName(TokenTools.getName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAccount());
        save(dto);
    }

    @Override
    public List<InvoiceItem> getItems(Long id) {
        QueryWrapper<InvoiceItem> queryWrapper=new QueryWrapper();
        queryWrapper.lambda()
                .eq(InvoiceItem::getInvoiceId,id)
                .eq(InvoiceItem::getSystemClientAccount,TokenTools.getAccount());

        List<InvoiceItem> list=list(queryWrapper);
        return list;
    }

    @Override
    public BigDecimal calculateSubAmount(InvoiceItemAmount dto) {
        return dto.getUnitPrice().multiply(new BigDecimal(dto.getQuantity()));
    }

    @Override
    public BigDecimal calculateSubTax(InvoiceItemTax dto) {
        return dto.getAmount().multiply(new BigDecimal(dto.getTaxRate()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
    }

    @Override
    public BigDecimal calculateSubTax(InvoiceItem item) {
        return item.getAmount().multiply(new BigDecimal(item.getTaxRate()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
    }

    @Override
    public BigDecimal calculateSubtotal(List<InvoiceItem> items) {
        return items.stream()
                .map(InvoiceItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTax(List<InvoiceItem> items) {
        return items.stream()
                .map(item -> item.getAmount()
                        .multiply(new BigDecimal(item.getTaxRate()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal tax) {
        return subtotal.add(tax);
    }

    @Override
    public BigDecimal calculateAllTotal(BigDecimal total, BigDecimal carryOverAmount) {
        return total.add(carryOverAmount);
    }
}
