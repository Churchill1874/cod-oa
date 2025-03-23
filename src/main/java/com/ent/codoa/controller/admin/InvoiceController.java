package com.ent.codoa.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.Invoice;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.invoice.InvoiceAdd;
import com.ent.codoa.pojo.req.invoice.InvoicePage;
import com.ent.codoa.pojo.resp.invoice.InvoiceInfoVO;
import com.ent.codoa.service.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags="支付管理-电子发票")
@RequestMapping("/admin/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/query")
    @ApiOperation(value = "分页查询电子发票生成记录",notes = "分页查询电子发票生成记录")
    public R<IPage<InvoiceInfoVO>> query(@RequestBody InvoicePage req){
        IPage<InvoiceInfoVO> iPage = invoiceService.queryPage(req);
        return R.ok(iPage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增发票",notes="新增发票")
    public R add(@RequestBody @Valid InvoiceAdd req){
        Invoice invoice = BeanUtil.toBean(req, Invoice.class);
        invoiceService.invoiceAdd(invoice);
        return R.ok(null);
    }

//    @PostMapping("/updateImage")
//    @ApiOperation(value = "新增发票",notes="新增发票")
//    public R updateImage(@RequestBody @Valid InvoiceUpdate req){
//        invoiceService.updateImage(req);
//        return R.ok(null);
//    }

    @PostMapping("/getInvoiceInfo")
    @ApiOperation(value="根据发票Id获取发票详情",notes = "根据发票Id获取发票详情")
    public R<InvoiceInfoVO> getInvoiceInfo(@RequestBody @Valid IdBase req){
        InvoiceInfoVO invoiceInfoVO = invoiceService.getInvoiceById(req.getId());
        return R.ok(invoiceInfoVO);
    }


}
