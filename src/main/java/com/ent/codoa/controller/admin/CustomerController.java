package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.pojo.req.customer.CustomerAdd;
import com.ent.codoa.pojo.req.customer.CustomerBaseUpdate;
import com.ent.codoa.pojo.req.customer.CustomerPage;
import com.ent.codoa.pojo.req.customer.CustomerStatusUpdate;
import com.ent.codoa.service.CustomerService;
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

    @PostMapping("/add")
    @ApiOperation(value = "添加客户", notes = "添加客户")
    public R add(@RequestBody @Valid CustomerAdd req) {
        Customer customer = BeanUtil.toBean(req, Customer.class);
        customerService.add(customer);
        return R.ok(null);
    }

    @PostMapping("/updateBase")
    @ApiOperation(value = "修改客户基本信息", notes = "修改客户基本信息")
    public R updateBase(@RequestBody @Valid CustomerBaseUpdate req) {
        customerService.updateBase(req);
        return R.ok(null);
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改客户账号状态", notes = "修改客户账号状态")
    public R updateStatus(@RequestBody @Valid CustomerStatusUpdate req) {
        customerService.updateStatus(req);
        return R.ok(null);
    }

}
