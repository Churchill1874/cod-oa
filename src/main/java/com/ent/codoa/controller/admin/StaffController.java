package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.Staff;
import com.ent.codoa.entity.SystemClient;
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

@Slf4j
@RestController
@Api(tags = "人事管理")
@RequestMapping("/admin/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/page")
    @ApiOperation(value = "分页系统用户", notes = "分页系统用户")
    public R<IPage<Staff>> page(@RequestBody SystemClientPage req) {

        return R.ok(null);
    }
}
