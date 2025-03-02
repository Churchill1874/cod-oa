package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.OperationLog;
import com.ent.codoa.pojo.req.PageBase;

public interface OperationLogService extends IService<OperationLog> {

    IPage<OperationLog> queryPage(PageBase dto);

    void add(OperationLog dto);

}
