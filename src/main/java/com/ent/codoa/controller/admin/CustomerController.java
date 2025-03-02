package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.pojo.req.customer.CustomerPage;
import com.ent.codoa.service.CustomerService;
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
@Api(tags = "客户管理")
@RequestMapping("/admin/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/page")
    @ApiOperation(value = "分页客户", notes = "分页客户")
    public R<IPage<Customer>> page(@RequestBody CustomerPage req) {
        IPage<Customer> iPage = customerService.queryPage(req);
        return R.ok(iPage);
    }

}
