package com.ent.codoa.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.entity.TaxRate;
import com.ent.codoa.mapper.TaxRateMapper;
import com.ent.codoa.service.TaxRateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxRateServiceImpl extends ServiceImpl<TaxRateMapper, TaxRate> implements TaxRateService {

    @Override
    public List<TaxRate> getTaxRateList() {
        QueryWrapper<TaxRate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(TaxRate::getDownLimit);
        return list(queryWrapper);
    }


}
