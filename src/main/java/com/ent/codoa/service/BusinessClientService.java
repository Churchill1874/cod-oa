package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.entity.BusinessClient;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.businessclient.BusinessClientBaseUpdate;

public interface BusinessClientService extends IService<BusinessClient> {

    IPage<BusinessClient> queryPage(PageBase pageBase);

    BusinessClient findByAccount(String account);

    void add(BusinessClient dto);

    void updateBaseInfo(BusinessClientBaseUpdate dto);

    void updateStatus(Long id, UserStatusEnum status);

}
