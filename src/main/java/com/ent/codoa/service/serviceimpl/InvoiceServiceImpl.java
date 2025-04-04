package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Invoice;
import com.ent.codoa.entity.InvoiceItem;
import com.ent.codoa.mapper.InvoiceMapper;
import com.ent.codoa.pojo.req.invoice.InvoicePage;
import com.ent.codoa.pojo.resp.invoice.InvoiceInfoVO;
import com.ent.codoa.service.InvoiceItemService;
import com.ent.codoa.service.InvoiceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {
    @Autowired
    private InvoiceItemService invoiceItemService;


    @Override
    public IPage<InvoiceInfoVO> queryPage(InvoicePage dto) {
        IPage<Invoice> iPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<InvoiceInfoVO> infoPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        QueryWrapper<Invoice> queryWrapper =new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dto.getRegistrationNumber()),Invoice::getRegistrationNumber,dto.getRegistrationNumber())
                .eq(StringUtils.isNotBlank(dto.getInvoiceNumber()),Invoice::getInvoiceNumber,dto.getInvoiceNumber())
                .eq(StringUtils.isNotBlank(dto.getSupplierName()),Invoice::getSupplierName,dto.getSupplierName())
                .eq(StringUtils.isNotBlank(dto.getCustomerName()),Invoice::getCustomerName,dto.getCustomerName())
                .eq(Invoice::getSystemClientAccount, TokenTools.getAccount())
                .orderByDesc(Invoice::getCreateTime);

        iPage=page(iPage,queryWrapper);
        if(CollectionUtils.isEmpty(iPage.getRecords())){
            return infoPage;
        }
        List<InvoiceInfoVO> list=new ArrayList<>();
        for(Invoice invoice:iPage.getRecords()){
            InvoiceInfoVO invoiceInfoVO=new InvoiceInfoVO();
            List<InvoiceItem> itemList=invoiceItemService.getItems(invoice.getId());
            invoiceInfoVO.setId(invoice.getId());
            invoiceInfoVO.setInvoiceNumber(invoice.getInvoiceNumber());
            invoiceInfoVO.setRegistrationNumber(invoice.getRegistrationNumber());
            invoiceInfoVO.setIssueDate(invoice.getIssueDate());
            invoiceInfoVO.setSupplierName(invoice.getSupplierName());
            invoiceInfoVO.setSupplierAddress(invoice.getSupplierAddress());
            invoiceInfoVO.setSupplierPhone(invoice.getSupplierPhone());
            invoiceInfoVO.setSupplierEmail(invoice.getSupplierEmail());
            invoiceInfoVO.setCustomerName(invoice.getCustomerName());
            invoiceInfoVO.setCustomerAddress(invoice.getCustomerAddress());
            invoiceInfoVO.setPreviousAmount(invoice.getPreviousAmount());
            invoiceInfoVO.setPaymentAmount(invoice.getPaymentAmount());
            invoiceInfoVO.setSubTotal(invoice.getSubTotal());
            invoiceInfoVO.setTaxAmount(invoice.getTaxAmount());
            invoiceInfoVO.setSubTaxTotal(invoice.getSubTaxTotal());
            invoiceInfoVO.setCarryOverAmount(invoice.getCarryOverAmount());
            invoiceInfoVO.setTotalAmount(invoice.getTotalAmount());
            invoiceInfoVO.setBankName(invoice.getBankName());
            invoiceInfoVO.setBranchName(invoice.getBranchName());
            invoiceInfoVO.setAccountNumber(invoice.getAccountNumber());
            invoiceInfoVO.setAccountHolder(invoice.getAccountHolder());
            invoiceInfoVO.setPaymentDueDate(invoice.getPaymentDueDate());
            invoiceInfoVO.setRemarks(invoice.getRemarks());
            invoiceInfoVO.setItems(itemList);
            list.add(invoiceInfoVO);
        }
        infoPage.setRecords(list);
        infoPage.setTotal(iPage.getTotal());
        infoPage.setPages(iPage.getPages());
        return infoPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invoiceAdd(Invoice dto) {
        QueryWrapper<Invoice> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Invoice::getRegistrationNumber,dto.getRegistrationNumber())
                .eq(Invoice::getInvoiceNumber,dto.getInvoiceNumber());
        Invoice invoice=getOne(queryWrapper);
        if(invoice!=null){
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("同一企业下，该发票的请求号码已存在");
            }else{
                throw new DataException("同一企業内でこの請求番号は既に存在しています");
            }
        }
        dto.setCreateName(TokenTools.getName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAccount());
//        BigDecimal totalAmount = dto.getItems().stream()
//                .map(InvoiceItem::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add); // 使用 BigDecimal 的加法
        BigDecimal subTotal = invoiceItemService.calculateSubtotal(dto.getItems());    //计算小计金额(不含税)
        BigDecimal tax = invoiceItemService.calculateTax(dto.getItems());  //计算消费税
        BigDecimal subTaxTotal = invoiceItemService.calculateTotal(subTotal, tax);   //计算总计金额
        BigDecimal totalAmount=invoiceItemService.calculateAllTotal(subTaxTotal,dto.getCarryOverAmount()); //计算此次请求总金额
        dto.setSubTotal(subTotal);
        dto.setTaxAmount(tax);
        dto.setSubTaxTotal(subTaxTotal);
        dto.setTotalAmount(totalAmount);

        save(dto);
//        updateById(dto);
        List<InvoiceItem> items = dto.getItems();
        if(CollectionUtils.isEmpty(items)){
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("至少需要一条以上的商品或服务明细数据");
            }else{
                throw new DataException("商品またはサービス明細が1件以上必要です");
            }
        }
        for (InvoiceItem item : items) {
            if(item.getDate()==null){
                throw new DataException("商品或服务提供日期不能为空");
            }else if(item.getTaxRate()==null){
                throw new DataException("税率不能为空");
            }else if(item.getAmount()==null){
                throw new DataException("商品或服务明细金额不能为空");
            }else if(item.getDescription()==null){
                throw new DataException("商品或服务明细内容不能为空");
            }else if(item.getQuantity()==null){
                throw new DataException("商品或服务数量不能为空");
            }else if(item.getTaxType()==null){
                throw new DataException("税率标识不能为空");
            }else if(item.getUnitPrice()==null){
                throw new DataException("商品或服务明细的单价不能为空");
            }else{
                item.setInvoiceId(dto.getId()); // 设置关联的发票ID
                invoiceItemService.add(item);
            }
        }
        LogTools.addLog("支付管理-电子发票", "新增了一条发票信息，信息：" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));

    }

//    @Override
//    public void updateImage(InvoiceUpdate dto) {
//        QueryWrapper<Invoice> queryWrapper=new QueryWrapper<>();
//        queryWrapper.lambda()
//                        .eq(Invoice::getId,dto.getId());
//        Invoice invoice=getOne(queryWrapper);
//        invoice.setImage(dto.getImage());
//        updateById(invoice);
//
//    }

    @Override
    public InvoiceInfoVO getInvoiceById(Long id) {
        QueryWrapper<Invoice> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Invoice::getId,id);
        Invoice invoice=getOne(queryWrapper);
        if(invoice!=null){
            InvoiceInfoVO invoiceInfoVO=new InvoiceInfoVO();
            List<InvoiceItem> itemList=invoiceItemService.getItems(invoice.getId());
            invoiceInfoVO.setId(invoice.getId());
            invoiceInfoVO.setInvoiceNumber(invoice.getInvoiceNumber());
            invoiceInfoVO.setRegistrationNumber(invoice.getRegistrationNumber());
            invoiceInfoVO.setIssueDate(invoice.getIssueDate());
            invoiceInfoVO.setSupplierName(invoice.getSupplierName());
            invoiceInfoVO.setSupplierAddress(invoice.getSupplierAddress());
            invoiceInfoVO.setSupplierPhone(invoice.getSupplierPhone());
            invoiceInfoVO.setSupplierEmail(invoice.getSupplierEmail());
            invoiceInfoVO.setCustomerName(invoice.getCustomerName());
            invoiceInfoVO.setCustomerAddress(invoice.getCustomerAddress());
            invoiceInfoVO.setPreviousAmount(invoice.getPreviousAmount());
            invoiceInfoVO.setPaymentAmount(invoice.getPaymentAmount());
            invoiceInfoVO.setSubTotal(invoice.getSubTotal());
            invoiceInfoVO.setTaxAmount(invoice.getTaxAmount());
            invoiceInfoVO.setSubTaxTotal(invoice.getSubTaxTotal());
            invoiceInfoVO.setCarryOverAmount(invoice.getCarryOverAmount());
            invoiceInfoVO.setTotalAmount(invoice.getTotalAmount());
            invoiceInfoVO.setBankName(invoice.getBankName());
            invoiceInfoVO.setBranchName(invoice.getBranchName());
            invoiceInfoVO.setAccountNumber(invoice.getAccountNumber());
            invoiceInfoVO.setAccountHolder(invoice.getAccountHolder());
            invoiceInfoVO.setPaymentDueDate(invoice.getPaymentDueDate());
            invoiceInfoVO.setRemarks(invoice.getRemarks());
            invoiceInfoVO.setItems(itemList);
            return invoiceInfoVO;
        }else{
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("未获取到任何数据");
            }else{
                throw new DataException("データを取得できませんでした");
            }
        }
    }

//    @Override
//    public String getInvoiceImage(Long id) {
//        QueryWrapper<Invoice> queryWrapper=new QueryWrapper<>();
//        queryWrapper.lambda()
//                .eq(Invoice::getId,id);
//        Invoice invoice = getOne(queryWrapper);
//        return invoice.getImage();
//    }
}
