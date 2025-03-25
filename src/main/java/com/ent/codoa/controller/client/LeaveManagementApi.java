package com.ent.codoa.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.entity.LeaveManagement;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.leavemanagement.LeaveManagementAdd;
import com.ent.codoa.pojo.req.leavemanagement.LeaveManagementPage;
import com.ent.codoa.service.LeaveManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@RestController
@Api(tags = "休假申请")
@RequestMapping("/client/leaveManagement")
public class LeaveManagementApi {

    @Autowired
    private LeaveManagementService leaveManagementService;

    @PostMapping("/page")
    @ApiOperation(value = "员工休假申请分页", notes = "员工休假申请分页")
    public R<IPage<LeaveManagement>> page(@RequestBody PageBase req) {
        IPage<LeaveManagement> iPage = leaveManagementService.clientPage(req);
        return R.ok(iPage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增休假申请", notes = "新增休假申请")
    public R add(@RequestBody @Valid LeaveManagementAdd req) {
        long leaveDays = ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate());
        if (leaveDays < 1){
            throw new DataException("申请休假时间至少一天或以上");
        }
        leaveManagementService.add(req);
        return R.ok(null);
    }



}
