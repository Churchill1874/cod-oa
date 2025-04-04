package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.common.annotation.StaffAuthCheck;
import com.ent.codoa.entity.Staff;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.staff.*;
import com.ent.codoa.pojo.req.systemclient.SystemClientPage;
import com.ent.codoa.service.StaffService;
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
@Api(tags = "人事管理")
@RequestMapping("/admin/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @StaffAuthCheck
    @PostMapping("/page")
    @ApiOperation(value = "分页员工", notes = "分页员工")
    public R<IPage<Staff>> page(@RequestBody StaffPage req) {
        IPage<Staff> iPage = staffService.queryPage(req);
        return R.ok(iPage);
    }

    @StaffAuthCheck
    @PostMapping("/add")
    @ApiOperation(value = "添加员工", notes = "添加员工")
    public R add(@RequestBody @Valid StaffAdd req) {
        Staff staff = BeanUtil.toBean(req, Staff.class);
        staffService.add(staff);
        return R.ok(null);
    }

    @StaffAuthCheck
    @PostMapping("/updateBase")
    @ApiOperation(value = "修改员工基本信息", notes = "修改员工基本信息")
    public R updateBase(@RequestBody @Valid StaffBaseUpdate req) {
        staffService.updateBase(req);
        return R.ok(null);
    }

    @StaffAuthCheck
    @PostMapping("/updateContract")
    @ApiOperation(value = "更新合同", notes = "更新合同")
    public R updateContract(@RequestBody @Valid StaffContractUpdate req) {
        staffService.updateContract(req.getId(), req.getContract());
        return R.ok(null);
    }

    @StaffAuthCheck
    @PostMapping("/aboutExpirePage")
    @ApiOperation(value = "即将到期的合同分页", notes = "即将到期的合同分页")
    public R<IPage<Staff>> aboutExpirePage(@RequestBody @Valid PageBase req) {
        return R.ok(staffService.aboutToExpirePage(req));
    }

}
