package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.entity.SalarySettlement;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.salarysettlement.SalarySettlementAdd;
import com.ent.codoa.pojo.req.salarysettlement.SalarySettlementPage;
import com.ent.codoa.pojo.req.salarysettlement.SalarySettlementStatistic;
import com.ent.codoa.service.SalarySettlementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "人事管理-薪资结算")
@RequestMapping("/admin/salarySettlement")
public class SalarySettlementController {

    @Autowired
    private SalarySettlementService salarySettlementService;

    @LoginCheck
    @PostMapping("/page")
    @ApiOperation(value = "薪资结算记录分页查询", notes = "薪资结算记录分页查询")
    public R<IPage<SalarySettlement>> page(@RequestBody SalarySettlementPage req) {
        IPage<SalarySettlement> iPage = salarySettlementService.queryPage(req);
        return R.ok(iPage);
    }

    @LoginCheck
    @PostMapping("/statisticsByDateAndAccount")
    @ApiOperation(value = "预估薪资结算数据查询 初始化数据", notes = "预估薪资结算数据查询 初始化数据")
    public R<SalarySettlement> statisticsByDateAndAccount(@RequestBody @Valid SalarySettlementStatistic req) {
        SalarySettlement salarySettlement = salarySettlementService.statisticsByDateAndAccount(req.getDate(), req.getAccount());
        return R.ok(salarySettlement);
    }

    @LoginCheck
    @PostMapping("/specifyDataStatistics")
    @ApiOperation(value = "修改后的指定数据查询 薪资统计", notes = "修改后的指定数据查询 薪资统计")
    public R<SalarySettlement> specifyDataStatistics(@RequestBody @Valid SalarySettlementAdd req) {
        SalarySettlement salarySettlement = salarySettlementService.specifyDataStatistics(req);
        return R.ok(salarySettlement);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增薪资结算记录", notes = "新增薪资结算记录")
    public R add(@RequestBody @Valid SalarySettlementAdd req) {
        salarySettlementService.add(req);
        return R.ok(null);
    }

}
