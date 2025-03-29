package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.entity.LeaveManagement;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.leavemanagement.LeaveManagementApproval;
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

@Slf4j
@RestController
@Api(tags = "休假管理")
@RequestMapping("/admin/leaveManagement")
public class LeaveManagementController {

    @Autowired
    private LeaveManagementService leaveManagementService;

    @LoginCheck
    @PostMapping("/page")
    @ApiOperation(value = "休假申请分页", notes = "休假申请分页")
    public R<IPage<LeaveManagement>> page(@RequestBody LeaveManagementPage req) {
        IPage<LeaveManagement> iPage = leaveManagementService.queryPage(req);
        return R.ok(iPage);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "根据休申请id删除", notes = "根据休申请id删除")
    public R delete(@RequestBody @Valid IdBase req) {
        leaveManagementService.delete(req.getId());
        return R.ok(null);
    }

    @PostMapping("/approval")
    @ApiOperation(value = "根据id审批休假状态", notes = "根据id审批休假状态")
    public R approval(@RequestBody @Valid LeaveManagementApproval req) {
        if (!"待审批".equals(req.getStatus()) && !"通过".equals(req.getStatus()) && !"拒绝".equals(req.getStatus())) {
            return R.failed("状态范围不在业务之内");
        }
        leaveManagementService.approval(req.getId(), req.getStatus());
        return R.ok(null);
    }

}
