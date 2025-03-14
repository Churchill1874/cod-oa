package com.ent.codoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Inventory;
import com.ent.codoa.entity.StockOperation;

public interface StockOperationService extends IService<StockOperation> {

    /**
     * 新增入库记录
     * @param dto
     */
    void stockIn(StockOperation dto);

    /**
     * 新增出库记录
     * @param dto
     */
    void stockOut(StockOperation dto);

    /**
     * 新增退货记录
     * @param dto
     */
    void stockReturn(StockOperation dto);
}
