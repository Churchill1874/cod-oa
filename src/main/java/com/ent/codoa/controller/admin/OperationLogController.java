package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.common.annotation.PlatformAuthCheck;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.entity.OperationLog;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.customer.CustomerPage;
import com.ent.codoa.service.CustomerService;
import com.ent.codoa.service.OperationLogService;
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
@Api(tags = "日志管理")
@RequestMapping("/admin/operationLog")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @PlatformAuthCheck
    @PostMapping("/page")
    @ApiOperation(value = "分页日志", notes = "分页日志")
    public R<IPage<OperationLog>> page(@RequestBody PageBase req) {
        IPage<OperationLog> iPage = operationLogService.queryPage(req);
        return R.ok(iPage);
    }

}
