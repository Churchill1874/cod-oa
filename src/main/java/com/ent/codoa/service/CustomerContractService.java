package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.CustomerContract;
import com.ent.codoa.pojo.req.customercontract.CustomerContractAdd;
import com.ent.codoa.pojo.req.customercontract.CustomerContractPage;
import com.ent.codoa.pojo.req.customercontract.CustomerContractUpdate;

public interface CustomerContractService extends IService<CustomerContract> {

    IPage<CustomerContract> queryPage(CustomerContractPage dto);

    void add(CustomerContractAdd dto);

    void updateBase(CustomerContractUpdate dto);

    void delete(Long id);

}
