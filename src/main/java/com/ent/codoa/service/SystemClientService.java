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
     * @param dto
     * @return
     */
    IPage<SystemClient> queryPage(SystemClientPage dto);

    /**
     * 添加系统用户
     * @param dto
     */
    void add(SystemClient dto);

    /**
     * 更新系统用户信息
     * @param dto
     */
    void editBaseInfo(SystemClient dto);

    /**
     * 删除系统用户
     * @param id
     */
    void delete(Long id);

}
