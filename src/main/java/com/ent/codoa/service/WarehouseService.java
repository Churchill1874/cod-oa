package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Warehouse;
import com.ent.codoa.pojo.req.warehouse.WarehouseBaseUpdate;
import com.ent.codoa.pojo.req.warehouse.WarehousePage;

import java.util.List;
import java.util.Map;

public interface WarehouseService extends IService<Warehouse> {
    /**
     * 根据仓库创建人账号分页获取仓库数据
     * @param dto
     * @return
     */
    IPage<Warehouse> queryPage(WarehousePage dto);

    /**
     * 新增仓库
     * @param dto
     */
    void add(Warehouse dto);

    /**
     * 修改仓库
     * @param dto
     */
    void updateBaseInfo(WarehouseBaseUpdate dto);

    /**
     * 根据仓库对应Id删除仓库
     * @param id
     */
    void delete(Long id);


    /**
     * 根据登录用户的系统用户账号获取名下所有仓库信息
     * @return
     */
    List<Map> findWarehouseList();

    /**
     * 根据仓库id查询仓库信息
     * @param id
     * @return
     */
    Warehouse findWarehouseById(Long id);





}
