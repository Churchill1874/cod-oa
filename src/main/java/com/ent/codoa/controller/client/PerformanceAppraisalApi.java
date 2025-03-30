package com.ent.codoa.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.CustomerAuthCheck;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.common.annotation.StaffAuthCheck;
import com.ent.codoa.entity.PerformanceAppraisal;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.service.PerformanceAppraisalService;
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
@Api(tags = "绩效考核")
@RequestMapping("/client/performanceAppraisal")
public class PerformanceAppraisalApi {

    @Autowired
    private PerformanceAppraisalService performanceAppraisalService;

    @StaffAuthCheck
    @PostMapping("/page")
    @ApiOperation(value = "员工分页绩效考核", notes = "员工分页绩效考核")
    public R<IPage<PerformanceAppraisal>> page(@RequestBody PageBase req) {
        IPage<PerformanceAppraisal> iPage = performanceAppraisalService.clientPage(req);
        return R.ok(iPage);
    }

}
