package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderInfoUpdate;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderPage;

public interface CustomerOrderService extends IService<CustomerOrder> {

    IPage<CustomerOrder> queryPage(CustomerOrderPage dto);

    void delete(Long id);

    void add(CustomerOrder dto);

    void updateInfo(CustomerOrderInfoUpdate dto);


}
