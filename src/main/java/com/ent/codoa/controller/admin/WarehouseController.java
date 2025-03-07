package com.ent.codoa.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.Warehouse;
import com.ent.codoa.pojo.req.warehouse.WarehouseAdd;
import com.ent.codoa.pojo.req.warehouse.WarehouseBaseUpdate;
import com.ent.codoa.pojo.req.warehouse.WarehousePage;
import com.ent.codoa.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "库存管理-仓库管理")
@RequestMapping("/admin/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService wirehouseService;

    @PostMapping("/page")
    @ApiOperation(value = "分页仓库信息",notes ="分页仓库信息" )
    public R<IPage<Warehouse>> page(@RequestBody WarehousePage req){
        IPage<Warehouse> ipage = wirehouseService.queryPage(req);
        return R.ok(ipage);
    }
    @PostMapping("/add")
    @ApiOperation(value = "新增仓库",notes = "新增仓库")
    public R add(@RequestBody WarehouseAdd req){
        Warehouse warehouse = BeanUtil.toBean(req, Warehouse.class);
        wirehouseService.add(warehouse);
        return R.ok(null);
    }
    @PostMapping("/updateBaseInfo")
    @ApiOperation(value = "修改仓库",notes = "修改仓库")
    public R updateBaseInfo(@RequestBody WarehouseBaseUpdate req){
        wirehouseService.updateBaseInfo(req);
        return R.ok(null);
    }

    @PostMapping("/getallwarehouse")
    @ApiOperation(value="获取所有仓库列表",notes = "获取所有仓库列表")
    public R<List<Warehouse>> findWarehouseList(){
        List<Warehouse> list=wirehouseService.findWarehouseList();
        return R.ok(list);
    }

    @PostMapping("/getwarehouse")
    @ApiOperation(value="根据Id获取仓库信息",notes = "根据Id获取仓库信息")
    public R<Warehouse> findWarehouseById(@RequestBody long id){
        Warehouse warehouse=wirehouseService.findWarehouseById(id);
        return R.ok(warehouse);
    }
}
