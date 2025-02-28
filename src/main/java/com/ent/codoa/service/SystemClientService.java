package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.SystemClient;
import com.ent.codoa.pojo.req.systemclient.SystemClientPage;

/**
 * 系统用户接口类
 */
public interface SystemClientService extends IService<SystemClient> {

    /**
     * 分页查询系统用户数据
     * @param po
     * @return
     */
    IPage<SystemClient> queryPage(SystemClientPage po);



}
