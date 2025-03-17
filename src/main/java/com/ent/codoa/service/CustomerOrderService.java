package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderInfoUpdate;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderPage;

import java.util.List;

public interface CustomerOrderService extends IService<CustomerOrder> {

    IPage<CustomerOrder> queryPage(CustomerOrderPage dto);

    void delete(Long id);

    void add(CustomerOrder dto);

    void updateInfo(CustomerOrderInfoUpdate dto);

    /**
     * 查询两年内的订单数据
     * 所谓两年 是从去年的1月开始到现在的数据
     * @return
     */
    List<CustomerOrder> withinTwoYears();

    /**
     * 查询近三个月的数据
     * @return
     */
    List<CustomerOrder> withinThreeMonth();

    /**
     * 客户数据分页
     * @param dto
     * @return
     */
    IPage<CustomerOrder> clientPage(PageBase dto);

}
