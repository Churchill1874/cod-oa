package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.Product;
import com.ent.codoa.pojo.resp.product.ProductQantityVO;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.product.*;
import com.ent.codoa.service.ProductService;
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
@Api(tags = "库存管理-商品管理")
@RequestMapping("admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/page")
    @ApiOperation(value = "分页商品信息",notes = "分页商品信息")
    public R<IPage<Product>> page(@RequestBody @Valid ProductPage req){
        IPage<Product> ipage=productService.queryPage(req);
        return R.ok(ipage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增商品",notes = "新增商品")
    public R add(@RequestBody @Valid ProductAdd req){
        Product product= BeanUtil.toBean(req,Product.class);
        productService.add(product);
        return R.ok(null);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改商品",notes = "修改商品")
    public R updateBaseProduct(@RequestBody @Valid ProductBaseUpdate req){
        productService.updateBaseProduct(req);
        return R.ok(null);
    }
//    @PostMapping("/getallproduct")
//    @ApiOperation(value = "根据仓库id获取所有商品",notes = "获取所有商品")
//    public R<List<Product>> getAllProduct(@RequestBody Long warehouseId){
//        List<Product> list=productService.getAllProduct(warehouseId);
//        return R.ok(list);
//    }
    @PostMapping("/getProduct")
    @ApiOperation(value = "根据ID获取商品",notes = "根据ID获取商品")
    public R<Product> getProduct(@RequestBody @Valid IdBase req){
        Product product=productService.getProductById(req.getId());
        return R.ok(product);
    }


    @PostMapping("/getALLProductQantity")
    @ApiOperation(value = "根据仓库Id获取所有商品库存", notes ="根据仓库Id获取所有商品库存")
    public R<IPage<ProductQantityVO>> getALLProductQantity(@RequestBody @Valid ProductWarehouseIdPage req) {
        IPage<ProductQantityVO> iPage = productService.getALLProductQantity(req);
        return R.ok(iPage);
    }


    @PostMapping("/getLowWarning")
    @ApiOperation(value = "根据仓库Id获取预警商品库存", notes ="根据仓库Id获取预警商品库存")
    public R<IPage<ProductQantityVO>> getLowWarning(@RequestBody @Valid ProductWarehouseIdPage req) {
        IPage<ProductQantityVO>list = productService.getLowWarning(req);
        return R.ok(list);
    }


}
