package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.CustomerWorkOrder;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderAdd;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderPage;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderStatusUpdate;

public interface CustomerWorkOrderService extends IService<CustomerWorkOrder> {

    IPage<CustomerWorkOrder> queryPage(CustomerWorkOrderPage dto);

    void updateStatus(CustomerWorkOrderStatusUpdate dto);

    void add(CustomerWorkOrderAdd dto);

    IPage<CustomerWorkOrder> clientPage(PageBase dto);

}
