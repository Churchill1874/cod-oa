package com.ent.codoa.controller.client;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.CustomerWorkOrder;
import com.ent.codoa.entity.CustomerWorkOrderDialogue;
import com.ent.codoa.pojo.req.IdPageBase;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderAdd;
import com.ent.codoa.pojo.req.customerworkorderdialogue.CustomerWorkOrderDialogueAdd;
import com.ent.codoa.service.CustomerWorkOrderDialogueService;
import com.ent.codoa.service.CustomerWorkOrderService;
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
@Api(tags = "客户管理-工单")
@RequestMapping("/client/customerWorkOrder")
public class CustomerWorkOrderApi {

    @Autowired
    private CustomerWorkOrderService customerWorkOrderService;
    @Autowired
    private CustomerWorkOrderDialogueService customerWorkOrderDialogueService;

    @PostMapping("/add")
    @ApiOperation(value = "客户提交工单", notes = "客户提交工单")
    public R add(@RequestBody @Valid CustomerWorkOrderAdd req) {
        customerWorkOrderService.add(req);
        return R.ok(null);
    }

    @PostMapping("/orderWorkPage")
    @ApiOperation(value = "分页客户工单", notes = "分页客户工单")
    public R<IPage<CustomerWorkOrder>> orderWorkPage(@RequestBody PageBase req) {
        return R.ok(customerWorkOrderService.clientPage(req));
    }

    @PostMapping("/customerReply")
    @ApiOperation(value = "客户指定工单留言", notes = "客户指定工单留言")
    public R customerReply(@RequestBody @Valid CustomerWorkOrderDialogueAdd req) {
        CustomerWorkOrderDialogue customerWorkOrderDialogue = BeanUtil.toBean(req, CustomerWorkOrderDialogue.class);
        customerWorkOrderDialogueService.customerReply(customerWorkOrderDialogue);
        return R.ok(null);
    }


    @PostMapping("/dialoguePage")
    @ApiOperation(value = "分页客户指定工单留言记录", notes = "分页客户指定工单留言记录")
    public R<IPage<CustomerWorkOrderDialogue>> page(@RequestBody IdPageBase req) {
        return R.ok(customerWorkOrderDialogueService.clientPage(req));
    }

}
