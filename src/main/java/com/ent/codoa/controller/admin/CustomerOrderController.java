package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.customer.CustomerBaseUpdate;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderAdd;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderInfoUpdate;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderPage;
import com.ent.codoa.service.CustomerOrderService;
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
@Api(tags = "客户管理-客户订单")
@RequestMapping("/admin/customerOrder")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @PostMapping("/page")
    @ApiOperation(value = "分页客户订单", notes = "分页客户订单")
    public R<IPage<CustomerOrder>> page(@RequestBody CustomerOrderPage req) {
        IPage<CustomerOrder> iPage = customerOrderService.queryPage(req);
        return R.ok(iPage);
    }



    @PostMapping("/add")
    @ApiOperation(value = "新增", notes = "新增")
    public R add(@RequestBody @Valid CustomerOrderAdd req) {
        CustomerOrder customerOrder = BeanUtil.toBean(req, CustomerOrder.class);
        customerOrderService.add(customerOrder);
        return R.ok(null);
    }



    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public R delete(@RequestBody @Valid IdBase req) {
        customerOrderService.delete(req.getId());
        return R.ok(null);
    }



    @PostMapping("/updateInfo")
    @ApiOperation(value = "修改", notes = "修改")
    public R updateInfo(@RequestBody @Valid CustomerOrderInfoUpdate req) {
        customerOrderService.updateInfo(req);
        return R.ok(null);
    }

}
