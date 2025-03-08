package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.common.constant.enums.OperationTypeEnum;
import com.ent.codoa.entity.Inventory;
import com.ent.codoa.pojo.req.inventory.InventoryPage;
import com.ent.codoa.pojo.req.inventory.InventoryPageByPro;

import java.util.List;

public interface InventoryService extends IService<Inventory> {
    /**
     * 分页查询库存记录
     * @param dto
     * @return
     */
    IPage<Inventory> queryPage(InventoryPage dto);


    /**
     * 新增库存信息-入库
     * @param dto
     */
    void stockIn(Inventory dto);

    /**
     * 出库
     * @param dto
     */
    void stockOut(Inventory dto);


    /**
     * 退货
     * @param dto
     */
    void inventoryReturn(Inventory dto);

    /**
     * 修改库存数量
     * @param warehouseId
     * @param productId
     * @param batchNumber
     * @param originalQuantity
     * @param quantity
     * @param operationTypeEnum
     */
    void updateQantity(Long warehouseId,Long productId,String batchNumber,Integer originalQuantity,Integer quantity,OperationTypeEnum operationTypeEnum);


    /**
     * 获取某个批次的库存
     * @param warehouseId
     * @param productId
     * @param batchNumber
     * @return
     */
    Inventory getInventory(Long warehouseId,Long productId,String batchNumber);


    /**
     * 根据仓库id查询即将在2个月后过期的库存
     * @param dto
     * @return
     */
    List<Inventory> getExpiring(InventoryPage dto);


    /**
     * 根据商品ID获取仓库库存
     * @param dto
     * @return
     */
    Integer getQantityByProduct(InventoryPageByPro dto);

}
