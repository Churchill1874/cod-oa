package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.constant.enums.InventoryStatusEnum;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Inventory;
import com.ent.codoa.entity.Product;
import com.ent.codoa.entity.base.BaseInfo;
import com.ent.codoa.pojo.req.product.ProductWarehouseIdPage;
import com.ent.codoa.pojo.resp.Product.ProductQantity;
import com.ent.codoa.mapper.ProductMapper;
import com.ent.codoa.pojo.req.inventory.InventoryPageByPro;
//import com.ent.codoa.pojo.req.product.ProductWarehouseIdPage;
import com.ent.codoa.pojo.req.product.ProductBaseUpdate;
import com.ent.codoa.pojo.req.product.ProductPage;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.InventoryService;
import com.ent.codoa.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        IPage<Product> iPage=new Page<>(dto.getPageNum(),dto.getPageSize());
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Product::getWarehouseId,dto.getWarehouseId())
                .eq(Product::getSystemClientAccount,TokenTools.getAdminAccount())
                .orderByDesc(Product::getCreateTime);
        iPage=page(iPage,queryWrapper);
        List<ProductQantity> newList = new ArrayList<>();
        for (Product product : iPage.getRecords()) {
            InventoryPageByPro inventoryPageByPro =new InventoryPageByPro();
            inventoryPageByPro.setWarehouseId(dto.getWarehouseId());
            inventoryPageByPro.setProductId(product.getId());
            Integer totalQantity = inventoryService.getQantityByProduct(inventoryPageByPro);
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


    //todo 这是一个代码例子 后面看懂了可以自己删了
    //整体思路 1.根据商品的维度进行分页查询
    //2.根据每页的商品数据 去查询库存表数据返回 将返回的数据统计 存入对应商品扩展统计的字段
    //3.拼装自定义的商品统计分页对象结构
    public IPage<ProductQantity> getLowWarningDemo (ProductWarehouseIdPage dto) {
        //最后要返回页面的分页对象
        IPage<ProductQantity> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());


        //分页查询商品 并根据创建时间排序
        IPage<Product> productIPage = new Page<>(iPage.getCurrent(), iPage.getSize());
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.lambda()
            .orderByDesc(Product::getCreateTime);
        productIPage = page(productIPage, productQueryWrapper);

        //如果没有查到商品的数据
        if (CollectionUtils.isEmpty(productIPage.getRecords())){
            //直接返回无数据的数据结构
            return iPage;
        }

        //如果有数据就根据商品分页返回的数据去查询库存表
        //提取外键 商品id集合 并且注意 不要用list存商品id 因为set特性能够去重自动合并重复的
        Set<Long> productIdSet = new HashSet<>();
        //根据商品id分组商品对象成map
        Map<Long, ProductQantity> productQantityMap = new HashMap<>();

        //遍历查到的商品
        for(Product product: productIPage.getRecords()){
            //存入set进行去重累计
            productIdSet.add(product.getId());

            //存入map 方便后面获取
            ProductQantity productQantity = BeanUtil.toBean(product, ProductQantity.class);
            productQantityMap.put(product.getId(), productQantity);
        }

        //根据商品id查询出来所有相关的商品和库存量大于0的库存
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();
        inventoryQueryWrapper.lambda()
            .in(Inventory::getProductId, productIdSet) //这里set或者List类型plus 会自己转成 in (x,x,x) 写法
            .eq(Inventory::getStatus, InventoryStatusEnum.TOBESOLD);
        List<Inventory> list = inventoryService.list(inventoryQueryWrapper);

        //如果这一页的商品 库存数量都是0 是不存在的,那么直接返回空数据的数据结构即可
        if (CollectionUtils.isEmpty(list)){
            return iPage;
        }

        //如果库存不为空 就根据商品id进行分组
        Map<Long, List<Inventory>> inventoryGroup = list.stream().collect(Collectors.groupingBy(Inventory::getId));

        //现在 库存已经根据商品id分组了 每个商品id都有自己的一个库存集合 每个库存集合单独统计总数就好
        //遍历 之前的那个商品的分组
        for(Long productId: productQantityMap.keySet()){
            //获取当前商品的value对象
            ProductQantity productQantity = productQantityMap.get(productId);
            //这个商品对象 目前就缺少一个 扩展字段的数据 库存总数, 下面我们就获取响应商品库存 ,然后累计库存 存入该字段

            List<Inventory> inventoryList = inventoryGroup.get(productId);
            //如果当前遍历的商品 不存在库存集合 就给统计该商品的总库存数量赋值为0 并且遍历下一个
            if (CollectionUtils.isEmpty(inventoryList)){
                productQantity.setTotalQuantity(0);
                continue;
            }

            //如果存在该商品的库存集合就累计
            //这里我用了jdk8 从集合对象的某个字段自动循环累计的函数 你也可以自己写for循环累计加这个字段的方式也没问题
            Integer totalQuantity = inventoryList.stream().mapToInt(Inventory::getQuantity).sum();
            productQantity.setTotalQuantity(totalQuantity);
        }

        //将商品基本的分页的数据 存入 你自定义返回分页的数据
        //iPage.setCurrent(); 已经在上面代码处理过了
        //iPage.setSize(); 已经在上面代码处理过了
        iPage.setRecords(new ArrayList<>(productQantityMap.values()));
        iPage.setTotal(productIPage.getTotal());
        iPage.setPages(productIPage.getPages());
        return iPage;
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
