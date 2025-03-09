package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.Inventory;
import com.ent.codoa.pojo.req.inventory.*;
import com.ent.codoa.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "库存管理-库存")
@RequestMapping("/admin/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询库存")
    public R<IPage<Inventory>> page(@RequestBody @Valid InventoryPage req) {
        IPage<Inventory> ipage = inventoryService.queryPage(req);
        return R.ok(ipage);
    }

    @PostMapping("/stockin")
    @ApiOperation(value = "入库")
    public R stockIn(@RequestBody @Valid InventoryStockIn req) {
        Inventory inventory = BeanUtil.toBean(req, Inventory.class);
        inventoryService.stockIn(inventory);
        return R.ok(null);
    }

    @PostMapping("/stockout")
    @ApiOperation(value = "出库")
    public R stockOut(@RequestBody @Valid InventoryStockOut req) {
        Inventory inventory = BeanUtil.toBean(req, Inventory.class);
        inventoryService.stockOut(inventory);
        return R.ok(null);
    }

    @PostMapping("/stockreturn")
    @ApiOperation(value = "退货")
    public R stockReturn(@RequestBody @Valid InventoryReturn req) {
        Inventory inventory = BeanUtil.toBean(req, Inventory.class);
        inventoryService.inventoryReturn(inventory);
        return R.ok(null);
    }

    @PostMapping("/getExpiring")
    @ApiOperation(value = "根据仓库Id获取即将过期的商品库存")
    public R<List<Inventory>> getExpiring(@RequestBody @Valid InventoryPage req) {
        List<Inventory> list = inventoryService.getExpiring(req);
        return R.ok(list);
    }

    @PostMapping("/getQantityByProduct")
    @ApiOperation(value = "根据商品Id获取商品库存")
    public R<Integer> getQantityByProduct(@RequestBody @Valid InventoryPageByPro req) {
        Integer qantity = inventoryService.getQantityByProduct(req);
        return R.ok(qantity);
    }
}
