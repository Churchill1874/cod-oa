package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Invoice;
import com.ent.codoa.pojo.req.invoice.InvoicePage;
import com.ent.codoa.pojo.resp.invoice.InvoiceInfoVO;

public interface InvoiceService extends IService<Invoice> {


    /**
     * 分页获取发票生成记录
     * @param dto
     * @return
     */
    IPage<InvoiceInfoVO> queryPage(InvoicePage dto);

    /**
     * 新增发票
     * @param dto
     */
    void invoiceAdd(Invoice dto);
//
//    /**
//     * dto
//     * @param dto
//     */
//    void updateImage(InvoiceUpdate dto);

    /**
     * 根据发票Id获取发票详情
     * @param id
     * @return
     */
    InvoiceInfoVO getInvoiceById(Long id);

//    /**
//     * 查看发票图片
//     * @param id
//     * @return
//     */
//    String getInvoiceImage(Long id);



}
