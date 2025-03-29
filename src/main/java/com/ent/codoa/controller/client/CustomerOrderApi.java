package com.ent.codoa.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.pojo.req.PageBase;
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

@Slf4j
@RestController
@Api(tags = "客户管理-客户订单")
@RequestMapping("/client/customerOrder")
public class CustomerOrderApi {

    @Autowired
    private CustomerOrderService customerOrderService;

    @LoginCheck
    @PostMapping("/page")
    @ApiOperation(value = "分页客户订单", notes = "分页客户订单")
    public R<IPage<CustomerOrder>> page(@RequestBody PageBase req) {
        IPage<CustomerOrder> iPage = customerOrderService.clientPage(req);
        return R.ok(iPage);
    }


}
