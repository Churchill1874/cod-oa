package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.constant.enums.InventoryStatusEnum;
import com.ent.codoa.common.constant.enums.OperationTypeEnum;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Inventory;
import com.ent.codoa.entity.StockOperation;
import com.ent.codoa.mapper.InventoryMapper;
import com.ent.codoa.pojo.req.inventory.InventoryPage;
import com.ent.codoa.pojo.req.inventory.InventoryPageByPro;
import com.ent.codoa.pojo.req.inventory.InventoryWarehousePage;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.InventoryService;
import com.ent.codoa.service.StockOperationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {
    @Autowired
    private StockOperationService stockInService;


    @Override
    public IPage<Inventory> queryPage(InventoryPage dto) {
        IPage<Inventory> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(dto.getProductId() != null && dto.getProductId()!=0, Inventory::getProductId, dto.getProductId())
                .eq(Inventory::getWarehouseId, dto.getWarehouseId())
                .like(StringUtils.isNotBlank(dto.getBatchNumber()),Inventory::getBatchNumber,dto.getBatchNumber())
                .eq(dto.getStatus()!=null,Inventory::getStatus, dto.getStatus())
                .eq(Inventory::getSystemClientAccount, TokenTools.getAccount())
                .orderByDesc(Inventory::getCreateTime);
        iPage = page(iPage, queryWrapper);
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void inventoryStockIn(Inventory dto) {
        Inventory inventory = getInventory(dto.getWarehouseId(), dto.getProductId(), dto.getBatchNumber());//校验批次是否存在
        LoginToken loginToken = TokenTools.getLoginToken(true);

        if (inventory != null) {    //已有该批次库存,直接修改现有库存数量
            Integer newQantity = inventory.getQuantity() + dto.getOriginalQuantity();
            updateQuantity(dto.getWarehouseId(), dto.getProductId(), dto.getBatchNumber(), dto.getOriginalQuantity(), newQantity, OperationTypeEnum.STOCKIN);
            StockOperation stockOperation = new StockOperation();
            stockOperation.setWarehouseId(dto.getWarehouseId());
            stockOperation.setProductId(dto.getProductId());
            stockOperation.setSupplier(dto.getSupplier());
            stockOperation.setQuantity(dto.getOriginalQuantity());
            stockOperation.setBatchNumber(dto.getBatchNumber());
            stockOperation.setProductionDate(dto.getProductionDate());
            stockOperation.setExpirationDate(dto.getExpirationDate());
            stockOperation.setOperationTime(LocalDateTime.now());
            stockInService.stockIn(stockOperation);          //插入库存操作记录表
            LogTools.addLog("库存管理-入库", "存在该批次，只更新库存数量，信息：" + JSONUtil.toJsonStr(dto), loginToken);
        } else {   //没有该批次库存，新增该批次库存
            dto.setQuantity(dto.getOriginalQuantity());
            dto.setQuantity(dto.getOriginalQuantity());
            dto.setStatus(InventoryStatusEnum.TOBESOLD);
            dto.setCreateName(TokenTools.getName());
            dto.setCreateTime(LocalDateTime.now());
            dto.setSystemClientAccount(TokenTools.getAccount());
            save(dto);
            StockOperation stockOperation = new StockOperation();
            stockOperation.setWarehouseId(dto.getWarehouseId());
            stockOperation.setProductId(dto.getProductId());
            stockOperation.setSupplier(dto.getSupplier());
            stockOperation.setQuantity(dto.getQuantity());
            stockOperation.setBatchNumber(dto.getBatchNumber());
            stockOperation.setProductionDate(dto.getProductionDate());
            stockOperation.setExpirationDate(dto.getExpirationDate());
            stockOperation.setOperationTime(LocalDateTime.now());
            stockInService.stockIn(stockOperation);          //插入库存操作记录表
            LogTools.addLog("库存管理-入库", "插入新批次库存，信息：" + JSONUtil.toJsonStr(dto), loginToken);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void inventoryStockOut(Inventory dto) {
        LoginToken loginToken = TokenTools.getLoginToken(true);
        Inventory inventory = getInventory(dto.getWarehouseId(), dto.getProductId(), dto.getBatchNumber());//校验批次库存数量
        if (dto.getQuantity() > inventory.getQuantity()) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("出库数量不能大于现有批次库存数量");
            }else{
                throw new DataException("出庫数量はロット在庫数量を超えることはできません");
            }
        }
        Integer newQantity = inventory.getQuantity() - dto.getQuantity();
        UpdateWrapper<Inventory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(Inventory::getQuantity, newQantity)
                .eq(Inventory::getWarehouseId, dto.getWarehouseId())
                .eq(Inventory::getProductId, dto.getProductId())
                .eq(Inventory::getBatchNumber, dto.getBatchNumber());
        update(updateWrapper);
        StockOperation stockOperation = new StockOperation();
        stockOperation.setWarehouseId(dto.getWarehouseId());
        stockOperation.setProductId(dto.getProductId());
        stockOperation.setSupplier(dto.getSupplier());
        stockOperation.setQuantity(dto.getQuantity());
        stockOperation.setBatchNumber(dto.getBatchNumber());
        stockOperation.setProductionDate(dto.getProductionDate());
        stockOperation.setExpirationDate(dto.getExpirationDate());
        stockOperation.setOperationTime(LocalDateTime.now());
        stockInService.stockOut(stockOperation);          //插入库存操作记录表
        LogTools.addLog("库存管理-出库", "出库库存，信息：" + JSONUtil.toJsonStr(dto), loginToken);
        Inventory newInventory = getInventory(dto.getWarehouseId(), dto.getProductId(), dto.getBatchNumber());//校验新库存数量是否大于0
        if (newInventory.getQuantity() == 0) {  //现有库存为0，更新状态为已销售
            UpdateWrapper<Inventory> newUpdateWrapper = new UpdateWrapper<>();
            newUpdateWrapper.lambda()
                    .set(Inventory::getStatus, InventoryStatusEnum.SOLDED)
                    .eq(Inventory::getWarehouseId, dto.getWarehouseId())
                    .eq(Inventory::getProductId, dto.getProductId())
                    .eq(Inventory::getBatchNumber, dto.getBatchNumber());
            update(newUpdateWrapper);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void inventoryReturn(Inventory dto) {
        LoginToken loginToken = TokenTools.getLoginToken(true);
        UpdateWrapper<Inventory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(Inventory::getStatus, InventoryStatusEnum.RETURNED)
                .eq(Inventory::getWarehouseId, dto.getWarehouseId())
                .eq(Inventory::getProductId, dto.getProductId())
                .eq(Inventory::getBatchNumber, dto.getBatchNumber());
        update(updateWrapper);
        StockOperation stockOperation = new StockOperation();
        Inventory inventory=getInventory(dto.getWarehouseId(),dto.getProductId() ,dto.getBatchNumber());
        if(inventory.getQuantity()==0 || InventoryStatusEnum.SOLDED==inventory.getStatus()){
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("已销售的库存批次不允许退货");
            }else{
                throw new DataException("出荷済みロットの返品は受領不可");
            }
        }
        stockOperation.setWarehouseId(dto.getWarehouseId());
        stockOperation.setProductId(dto.getProductId());
        stockOperation.setSupplier(dto.getSupplier());
        stockOperation.setQuantity(inventory.getQuantity());
        stockOperation.setBatchNumber(dto.getBatchNumber());
        stockOperation.setProductionDate(dto.getProductionDate());
        stockOperation.setExpirationDate(dto.getExpirationDate());
        stockOperation.setOperationTime(LocalDateTime.now());
        stockInService.stockReturn(stockOperation);          //插入库存操作记录表
        LogTools.addLog("库存管理-退货", "退货了一批库存，信息：" + JSONUtil.toJsonStr(dto), loginToken);


    }

    @Override
    public void updateQuantity(Long warehouseId, Long productId, String batchNumber, Integer originalQuantity, Integer quantity, OperationTypeEnum operationTypeEnum) {
        UpdateWrapper<Inventory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(Inventory::getQuantity, quantity)
                .eq(Inventory::getWarehouseId, warehouseId)
                .eq(Inventory::getProductId, productId)
                .eq(Inventory::getBatchNumber, batchNumber);
//        if(OperationTypeEnum.STOCKIN==operationTypeEnum){
//            updateWrapper.lambda()      //入库
//                    .set(Inventory::getOriginalQuantity,originalQuantity)
//                    .set(Inventory::getQuantity,quantity)
//                    .eq(Inventory::getWarehouseId,warehouseId)
//                    .eq(Inventory::getProductId,productId)
//                    .eq(Inventory::getBatchNumber,batchNumber);
//        }else if(OperationTypeEnum.STOCKOUT==operationTypeEnum){
//            updateWrapper.lambda()     //出库
//                    .set(Inventory::getQuantity,quantity)
//                    .eq(Inventory::getWarehouseId,warehouseId)
//                    .eq(Inventory::getProductId,productId)
//                    .eq(Inventory::getBatchNumber,batchNumber);
//        }else{
//            throw new DataException("操作参数传入错误，只支持出库和退货");
//        }

        update(updateWrapper);
    }


    @Override
    public Inventory getInventory(Long warehouseId, Long productId, String batchNumber) {
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Inventory::getWarehouseId, warehouseId)
                .eq(Inventory::getProductId, productId)
                .eq(Inventory::getBatchNumber, batchNumber)
                .eq(Inventory::getSystemClientAccount, TokenTools.getAccount());
        return getOne(queryWrapper);
    }


    @Override
    public IPage<Inventory> getExpiring(InventoryWarehousePage dto) {
//
//        IPage<Inventory> iPage=new Page<>(dto.getPageNum(),dto.getPageSize());
//        return inventoryMapper.selectExpiring(iPage);
        IPage<Inventory> iPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        QueryWrapper<Inventory> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .le(Inventory::getExpirationDate,LocalDate.now().plusMonths(2))
                .eq(Inventory::getWarehouseId,dto.getWarehouseId())
                .eq(Inventory::getSystemClientAccount,TokenTools.getAccount())
                .eq(Inventory::getStatus,InventoryStatusEnum.TOBESOLD);


        return page(iPage,queryWrapper);

//        LocalDate today = LocalDate.now();
//        LocalDate twoMonthsLater = today.plusMonths(2);
//
//        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda()
//                .eq(Inventory::getWarehouseId, dto.getWarehouseId())
//                .eq(Inventory::getStatus,dto.getStatus())
//                .eq(Inventory::getSystemClientAccount, TokenTools.getAccount());
//        List<Inventory> list = list(queryWrapper);
//        List<Inventory> newList = new ArrayList<>();
//        for (Inventory inventory : list) {
//            LocalDate expirationDate = inventory.getExpirationDate(); // 过期日期
//            if (expirationDate != null && inventory.getStatus()==InventoryStatusEnum.TOBESOLD && //有效库存
//                    !expirationDate.isBefore(today) && // 过期日期在当前日期之后
//                    !expirationDate.isAfter(twoMonthsLater)) { // 过期日期在 2 个月后日期之前
//                newList.add(inventory);
//            }
//        }
//        return newList;
    }

    @Override
    public Integer getQuantityByProduct (InventoryPageByPro dto){
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Inventory::getWarehouseId, dto.getWarehouseId())
                .eq(Inventory::getProductId, dto.getProductId())
                .eq(Inventory::getSystemClientAccount, TokenTools.getAccount())
                .orderByDesc(Inventory::getCreateTime);
        List<Inventory> list = list(queryWrapper);
        Integer totalQantity=0;
        for (Inventory inventory : list) {
            if(inventory.getStatus()==InventoryStatusEnum.TOBESOLD){ //待销售的库存才算入商品实时库存中
                totalQantity+=inventory.getQuantity();
            }

        }
        return totalQantity;
    }
}
