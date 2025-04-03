package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.StaffAuthCheck;
import com.ent.codoa.entity.TaxRate;
import com.ent.codoa.service.TaxRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "税率")
@RequestMapping("/admin/taxRate")
public class TaxRateController {

    @Autowired
    private TaxRateService taxRateService;

    @StaffAuthCheck
    @PostMapping("/getTaxRateList")
    @ApiOperation(value = "所得税比例", notes = "所得税比例")
    public R<List<String>> getTaxRateList() {
        List<TaxRate> taxRateList = taxRateService.getTaxRateList();
        if (CollectionUtils.isEmpty(taxRateList)){
            return R.ok(null);
        }

        List<String> list = new ArrayList<>();
        for(TaxRate taxRate: taxRateList){
            if (taxRate.getUpLimit().compareTo(new BigDecimal("-1")) == 0){
                list.add("大于 "+ taxRate.getDownLimit() + "万円");
                continue;
            }

            list.add(taxRate.getDownLimit() + "万円 ~ " + taxRate.getUpLimit() + "万円");
        }

        return R.ok(list);
    }



}
