package com.ent.codoa.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.entity.SalarySettlement;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.service.SalarySettlementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "结算薪资记录")
@RequestMapping("/client/salarySettlement")
public class SalarySettlementApi {

    @Autowired
    private SalarySettlementService salarySettlementService;

    @LoginCheck
    @PostMapping("/page")
    @ApiOperation(value = "薪资结算记录分页查询", notes = "薪资结算记录分页查询")
    public R<IPage<SalarySettlement>> page(@RequestBody PageBase req) {
        IPage<SalarySettlement> iPage = salarySettlementService.clientPage(req);
        return R.ok(iPage);
    }

}
