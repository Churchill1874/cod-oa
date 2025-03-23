package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.SalarySettlement;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.salarysettlement.SalarySettlementAdd;
import com.ent.codoa.pojo.req.salarysettlement.SalarySettlementPage;

public interface SalarySettlementService extends IService<SalarySettlement> {

    IPage<SalarySettlement> queryPage(SalarySettlementPage dto);

    void add(SalarySettlementAdd dto);

    void delete(Long id);

    IPage<SalarySettlement> clientPage(PageBase dto);

    SalarySettlement statisticsByDateAndAccount (String date, String account);

    SalarySettlement specifyDataStatistics(SalarySettlementAdd dto);

}
