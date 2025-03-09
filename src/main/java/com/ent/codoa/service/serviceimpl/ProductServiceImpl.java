package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Product;
import com.ent.codoa.entity.Warehouse;
import com.ent.codoa.mapper.ProductMapper;
import com.ent.codoa.mapper.WarehouseMapper;
import com.ent.codoa.pojo.req.product.ProductBaseUpdate;
import com.ent.codoa.pojo.req.product.ProductPage;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
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
        LoginToken loginToken=TokenTools.getLoginToken(true);
        dto.setCreateName(TokenTools.getAdminName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAdminAccount());
        save(dto);
        LogTools.addLog("库存管理-新增商品","新增了一个商品,信息："+ JSONUtil.toJsonStr(dto),loginToken);
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
}
