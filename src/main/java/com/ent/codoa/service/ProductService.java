package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Product;
import com.ent.codoa.pojo.resp.Product.ProductQantity;
import com.ent.codoa.pojo.req.product.ProductWarehouseIdPage;
import com.ent.codoa.pojo.req.product.ProductBaseUpdate;
import com.ent.codoa.pojo.req.product.ProductPage;

import java.util.List;

public interface ProductService extends IService<Product> {
    /**
     * 分页查询商品
     * @param dto
     * @return
     */
    IPage<Product> queryPage(ProductPage dto);

    /**
     * 新增商品
     * @param dto
     */
    void add(Product dto);

    /**
     * 修改商品
     * @param dto
     */
    void updateBaseProduct(ProductBaseUpdate dto);


    /**
     * 根据库存Id获取所有的商品信息
     * @return
     */
    List<Product> getAllProduct(Long warehouseId);

    /**
     * 根据ID查询商品信息
     * @param id
     * @return
     */
    Product getProductById(Long id);



    /**
     * 根据仓库ID获取仓库库存
     * @param dto  仓库id
     * @return
     */
    IPage<ProductQantity> getALLProductQantity(ProductWarehouseIdPage dto);


    /**
     * 根据仓库id获取低于预警的商品库存信息
     * @param dto
     * @return
     */
    IPage<ProductQantity> getLowWarning(ProductWarehouseIdPage dto);


}
