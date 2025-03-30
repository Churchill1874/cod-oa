package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.common.annotation.StaffAuthCheck;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.entity.PerformanceAppraisalSetting;
import com.ent.codoa.pojo.req.customer.CustomerPage;
import com.ent.codoa.pojo.req.performanceappraisalsetting.PerformanceAppraisalSettingAdd;
import com.ent.codoa.service.PerformanceAppraisalSettingService;
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
@Api(tags = "绩效考核配置")
@RequestMapping("/admin/performanceAppraisalSetting")
public class PerformanceAppraisalSettingController {

    @Autowired
    private PerformanceAppraisalSettingService performanceAppraisalSettingService;

    @StaffAuthCheck
    @PostMapping("/querySetting")
    @ApiOperation(value = "查询绩效考核当前配置", notes = "查询绩效考核当前配置")
    public R<PerformanceAppraisalSetting> querySetting() {
        PerformanceAppraisalSetting performanceAppraisalSetting =  performanceAppraisalSettingService.findBySystemClientAccount();
        //帮前端初始化一个结构 如果为空
        if (performanceAppraisalSetting == null){
            performanceAppraisalSetting = new PerformanceAppraisalSetting();
        }
        return R.ok(performanceAppraisalSetting);
    }

    @StaffAuthCheck
    @PostMapping("/setting")
    @ApiOperation(value = "配置绩效考核", notes = "配置绩效考核")
    public R setting(@RequestBody @Valid PerformanceAppraisalSettingAdd req) {
        performanceAppraisalSettingService.setting(req);
        return R.ok(null);
    }

}
