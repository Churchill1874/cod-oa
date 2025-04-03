package com.ent.codoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.TaxRate;

import java.util.List;

public interface TaxRateService extends IService<TaxRate> {

    List<TaxRate> getTaxRateList();

}
