package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.common.annotation.StaffAuthCheck;
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
import java.math.BigDecimal;

@Slf4j
@RestController
@Api(tags = "人事管理-薪资结算")
@RequestMapping("/admin/salarySettlement")
public class SalarySettlementController {

    @Autowired
    private SalarySettlementService salarySettlementService;

    @StaffAuthCheck
    @PostMapping("/page")
    @ApiOperation(value = "薪资结算记录分页查询", notes = "薪资结算记录分页查询")
    public R<IPage<SalarySettlement>> page(@RequestBody SalarySettlementPage req) {
        IPage<SalarySettlement> iPage = salarySettlementService.queryPage(req);
        return R.ok(iPage);
    }

    @StaffAuthCheck
    @PostMapping("/statisticsByDateAndAccount")
    @ApiOperation(value = "预估薪资结算数据查询 初始化数据", notes = "预估薪资结算数据查询 初始化数据")
    public R<SalarySettlement> statisticsByDateAndAccount(@RequestBody @Valid SalarySettlementStatistic req) {
        SalarySettlement salarySettlement = salarySettlementService.statisticsByDateAndAccount(req.getDate(), req.getAccount());
        return R.ok(salarySettlement);
    }

    @StaffAuthCheck
    @PostMapping("/specifyDataStatistics")
    @ApiOperation(value = "修改后的指定数据查询 薪资统计", notes = "修改后的指定数据查询 薪资统计")
    public R<SalarySettlement> specifyDataStatistics(@RequestBody @Valid SalarySettlementAdd req) {
        initData(req);
        SalarySettlement salarySettlement = salarySettlementService.specifyDataStatistics(req);
        return R.ok(salarySettlement);
    }

    @StaffAuthCheck
    @PostMapping("/add")
    @ApiOperation(value = "新增薪资结算记录", notes = "新增薪资结算记录")
    public R add(@RequestBody @Valid SalarySettlementAdd req) {
        initData(req);
        salarySettlementService.add(req);
        return R.ok(null);
    }

    private void initData(SalarySettlementAdd dto){
        if (dto.getWeekdayOvertimePayRate() == null){
            dto.setWeekdayOvertimePayRate(BigDecimal.ZERO);
        }
        if (dto.getWeekdayOvertimeAmount() == null){
            dto.setWeekdayOvertimeAmount(BigDecimal.ZERO);
        }
        if (dto.getWeekdaysOvertime() == null){
            dto.setWeekdaysOvertime(BigDecimal.ZERO);
        }
        if (dto.getWeekendOvertimePayRate() == null){
            dto.setWeekendOvertimePayRate(BigDecimal.ZERO);
        }
        if (dto.getWeekendOvertimeAmount() == null){
            dto.setWeekendOvertimeAmount(BigDecimal.ZERO);
        }
        if (dto.getWeekendsOvertime() == null){
            dto.setWeekendsOvertime(BigDecimal.ZERO);
        }
        if (dto.getAbsenceDays() == null){
            dto.setAbsenceDays(BigDecimal.ZERO);
        }
        if (dto.getLateHours() == null){
            dto.setLateHours(BigDecimal.ZERO);
        }
        if (dto.getLeaveEarly() == null){
            dto.setLeaveEarly(BigDecimal.ZERO);
        }
        if (dto.getExtraCalculationAmount() == null){
            dto.setExtraCalculationAmount(BigDecimal.ZERO);
        }
        if (dto.getPayTaxesRate() == null){
            dto.setPayTaxesRate(0);
        }
        if (dto.getResidentTaxRate() == null){
            dto.setResidentTaxRate(0);
        }
        if (dto.getResidentTaxAmount() == null){
            dto.setResidentTaxAmount(BigDecimal.ZERO);
        }
    }

}
