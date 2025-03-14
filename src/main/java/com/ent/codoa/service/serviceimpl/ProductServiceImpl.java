package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Product;
import com.ent.codoa.pojo.req.product.ProductWarehouseIdPage;
import com.ent.codoa.pojo.resp.Product.ProductQantity;
import com.ent.codoa.mapper.ProductMapper;
import com.ent.codoa.pojo.req.inventory.InventoryPageByPro;
import com.ent.codoa.pojo.req.product.ProductBaseUpdate;
import com.ent.codoa.pojo.req.product.ProductPage;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.InventoryService;
import com.ent.codoa.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    InventoryService inventoryService;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public IPage<Product> queryPage(ProductPage dto) {
        IPage<Product> iPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(dto.getName()),Product::getName,dto.getName())
                .eq(StringUtils.isNotBlank(dto.getCategory()),Product::getCategory,dto.getCategory())
                .eq(Product::getSystemClientAccount,TokenTools.getAdminAccount())
                .eq(Product::getWarehouseId,dto.getWarehouseId())
                .orderByDesc(Product::getCreateTime);
        iPage=page(iPage,queryWrapper);
        return iPage;
    }

    @Override
    public void add(Product dto) {
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Product::getName,dto.getName())
                .eq(Product::getCategory,dto.getCategory())
                .eq(Product::getWarehouseId,dto.getWarehouseId())
                .eq(Product::getSystemClientAccount,TokenTools.getAdminAccount());
        Product product=getOne(queryWrapper);
        if(product!=null&&product.getName().equals(dto.getName())&&
                product.getCategory().equals(dto.getCategory())&&
                product.getWarehouseId().equals(dto.getWarehouseId())
        ){
            throw new DataException("同类商品的商品名称重复");
        }else{
            LoginToken loginToken=TokenTools.getLoginToken(true);
            dto.setCreateName(TokenTools.getAdminName());
            dto.setCreateTime(LocalDateTime.now());
            dto.setSystemClientAccount(TokenTools.getAdminAccount());
            save(dto);
            LogTools.addLog("库存管理-新增商品","新增了一个商品,信息："+ JSONUtil.toJsonStr(dto),loginToken);
        }

    }

    @Override
    public void updateBaseProduct(ProductBaseUpdate dto) {
        LoginToken loginToken=TokenTools.getLoginToken(true);
        UpdateWrapper<Product> updateWrapper=new UpdateWrapper();
        updateWrapper.lambda()
                .set(Product::getName,dto.getName())
                .set(Product::getDescription,dto.getDescription())
                .set(Product::getCategory,dto.getCategory())
                .set(Product::getUnit,dto.getUnit())
                .set(Product::getWarningQuantity,dto.getWarningQuantity())
                .eq(Product::getId,dto.getId());
        update(updateWrapper);
        LogTools.addLog("库存管理-修改商品","修改了一条商品，信息："+JSONUtil.toJsonStr(dto),loginToken);
    }

    @Override
    public List<Product> getAllProduct(Long warehouseId) {
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Product::getSystemClientAccount,TokenTools.getAdminAccount())
                .eq(Product::getWarehouseId,warehouseId)
                .orderByDesc(Product::getCreateTime);
        List<Product> list=list(queryWrapper);
        Set<Product> set=new HashSet<>(list);
        List<Product> distinctList=new ArrayList<>(set);
        return distinctList;
    }

    @Override
    public Product getProductById(Long id) {
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Product::getId,id);
        return getOne(queryWrapper);
    }

    @Override
    public IPage<ProductQantity> getALLProductQantity (ProductWarehouseIdPage dto){
        IPage<ProductQantity> iPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<Product> ProductIPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Product::getWarehouseId,dto.getWarehouseId())
                .eq(Product::getSystemClientAccount,TokenTools.getAdminAccount())
                .orderByDesc(Product::getCreateTime);
        ProductIPage=page(ProductIPage,queryWrapper);
        List<ProductQantity> newList = new ArrayList<>();
        if(CollectionUtils.isEmpty(ProductIPage.getRecords())){
            return iPage;
        }
        for (Product product : ProductIPage.getRecords()) {
            InventoryPageByPro inventoryPageByPro =new InventoryPageByPro();
            inventoryPageByPro.setWarehouseId(dto.getWarehouseId());
            inventoryPageByPro.setProductId(product.getId());
            Integer totalQantity = inventoryService.getQuantityByProduct(inventoryPageByPro);
            ProductQantity productQantity =new ProductQantity();
            productQantity.setWarehouseId(dto.getWarehouseId());
            productQantity.setId(product.getId());
            productQantity.setName(product.getName());
            productQantity.setCategory(product.getCategory());
            productQantity.setUnit(product.getUnit());
            productQantity.setWarningQuantity(product.getWarningQuantity());
            productQantity.setTotalQuantity(totalQantity);
            newList.add(productQantity);
        }
        IPage<ProductQantity> newIPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        newIPage.setRecords(newList);
        newIPage.setTotal(iPage.getTotal());
        newIPage.setPages(iPage.getPages());
        return newIPage;
    }



    @Override
    public IPage<ProductQantity> getLowWarning (ProductWarehouseIdPage dto){
        IPage<ProductQantity> iPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        return productMapper.findProductsWithStockWarning(iPage);
//        IPage<Product> iPage=new Page<>(dto.getPageNum(),dto.getPageSize());
//        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
//        queryWrapper.lambda()
//                .eq(Product::getWarehouseId,dto.getWarehouseId())
//                .eq(Product::getSystemClientAccount,TokenTools.getAdminAccount())
//                .orderByDesc(Product::getCreateTime);
//        iPage=page(iPage,queryWrapper);
//        List<ProductQantity> newList = new ArrayList<>();
//        for(Product product:iPage.getRecords()){
//            InventoryPageByPro inventoryPageByPro =new InventoryPageByPro();
//            inventoryPageByPro.setWarehouseId(dto.getWarehouseId());
//            inventoryPageByPro.setProductId(product.getId());
//            Integer totalQantity = inventoryService.getQantityByProduct(inventoryPageByPro);
//            if(product.getWarningQuantity()>=totalQantity){
//                ProductQantity productQantity =new ProductQantity();
//                productQantity.setWarehouseId(dto.getWarehouseId());
//                productQantity.setId(product.getId());
//                productQantity.setName(product.getName());
//                productQantity.setCategory(product.getCategory());
//                productQantity.setUnit(product.getUnit());
//                productQantity.setWarningQuantity(product.getWarningQuantity());
//                productQantity.setTotalQuantity(totalQantity);
//                newList.add(productQantity);
//            }
//        }
//        IPage<ProductQantity> newIPage=new Page<>(dto.getPageNum(),dto.getPageSize());
//        newIPage.setRecords(newList);
//        newIPage.setTotal(iPage.getTotal());
//        newIPage.setPages(iPage.getPages());
//        return newIPage;
    }
}
