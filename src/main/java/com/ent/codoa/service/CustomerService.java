package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.pojo.req.customer.CustomerBaseUpdate;
import com.ent.codoa.pojo.req.customer.CustomerPage;

/**
 * 客户管理接口
 */
public interface CustomerService extends IService<Customer> {

    IPage<Customer> queryPage(CustomerPage dto);

    void add(Customer dto);

    void updateBaseInfo(CustomerBaseUpdate dto);

    Customer findByAccount(String account);




}
