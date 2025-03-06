package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.CustomerWorkOrder;
import com.ent.codoa.entity.CustomerWorkOrderDialogue;
import com.ent.codoa.entity.OperationLog;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderPage;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderStatusUpdate;
import com.ent.codoa.pojo.req.customerworkorderdialogue.CustomerWorkOrderDialogueAdd;
import com.ent.codoa.pojo.req.customerworkorderdialogue.CustomerWorkOrderDialoguePage;
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
@Api(tags = "客户管理-工单记录")
@RequestMapping("/admin/customerWorkOrder")
public class CustomerWorkOrderController {

    @Autowired
    private CustomerWorkOrderService customerWorkOrderService;
    @Autowired
    private CustomerWorkOrderDialogueService customerWorkOrderDialogueService;

    @PostMapping("/page")
    @ApiOperation(value = "分页工单记录", notes = "分页工单记录")
    public R<IPage<CustomerWorkOrder>> page(@RequestBody CustomerWorkOrderPage req) {
        IPage<CustomerWorkOrder> iPage = customerWorkOrderService.queryPage(req);
        return R.ok(iPage);
    }


    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改工单记录状态", notes = "修改工单记录状态")
    public R updateStatus(@RequestBody @Valid CustomerWorkOrderStatusUpdate req) {
        customerWorkOrderService.updateStatus(req);
        return R.ok(null);
    }


    @PostMapping("/dialoguePage")
    @ApiOperation(value = "根据工单id 分页工单描述对话记录", notes = "根据工单id 分页工单描述对话记录")
    public R<IPage<CustomerWorkOrderDialogue>> dialoguePage(@RequestBody @Valid CustomerWorkOrderDialoguePage req) {
        IPage<CustomerWorkOrderDialogue> iPage = customerWorkOrderDialogueService.queryPage(req);
        return R.ok(iPage);
    }


    @PostMapping("/systemClientReply")
    @ApiOperation(value = "系统用户回复工单", notes = "系统用户回复工单")
    public R systemClientReply(@RequestBody @Valid CustomerWorkOrderDialogueAdd req) {
        CustomerWorkOrderDialogue customerWorkOrderDialogue = BeanUtil.toBean(req, CustomerWorkOrderDialogue.class);
        customerWorkOrderDialogueService.systemClientReply(customerWorkOrderDialogue);
        return R.ok(null);
    }




}
