package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.pojo.req.customer.CustomerBaseUpdate;
import com.ent.codoa.pojo.req.customer.CustomerPage;
import com.ent.codoa.pojo.req.customer.CustomerStatusUpdate;

/**
 * 客户管理接口
 */
public interface CustomerService extends IService<Customer> {

    IPage<Customer> queryPage(CustomerPage dto);

    void add(Customer dto);

    void updateBase(CustomerBaseUpdate dto);

    void updateStatus(CustomerStatusUpdate dto);

    Customer findByAccount(String account);






}
