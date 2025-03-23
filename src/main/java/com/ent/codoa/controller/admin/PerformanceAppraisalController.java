package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.PerformanceAppraisal;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.performanceappraisal.PerformanceAppraisalAdd;
import com.ent.codoa.pojo.req.performanceappraisal.PerformanceAppraisalPage;
import com.ent.codoa.service.PerformanceAppraisalService;
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
@Api(tags = "绩效考核")
@RequestMapping("/admin/performanceAppraisal")
public class PerformanceAppraisalController {

    @Autowired
    private PerformanceAppraisalService performanceAppraisalService;

    @PostMapping("/page")
    @ApiOperation(value = "分页绩效考核", notes = "分页绩效考核")
    public R<IPage<PerformanceAppraisal>> page(@RequestBody PerformanceAppraisalPage req) {
        IPage<PerformanceAppraisal> iPage = performanceAppraisalService.queryPage(req);
        return R.ok(iPage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "提交绩效考核", notes = "提交绩效考核")
    public R add(@RequestBody @Valid PerformanceAppraisalAdd req) {
        performanceAppraisalService.add(req);
        return R.ok(null);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "根据绩效id删除", notes = "根据绩效id删除")
    public R delete(@RequestBody @Valid IdBase req) {
        performanceAppraisalService.delete(req.getId());
        return R.ok(null);
    }


}
