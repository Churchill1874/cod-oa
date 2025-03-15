package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.CustomerContract;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.customercontract.CustomerContractAdd;
import com.ent.codoa.pojo.req.customercontract.CustomerContractPage;
import com.ent.codoa.pojo.req.customercontract.CustomerContractUpdate;
import com.ent.codoa.service.CustomerContractService;
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
@Api(tags = "客户管理-合同管理")
@RequestMapping("/admin/customerContract")
public class CustomerContractController {

    @Autowired
    private CustomerContractService customerContractService;

    @PostMapping("/page")
    @ApiOperation(value = "分页", notes = "分页")
    public R<IPage<CustomerContract>> page(@RequestBody CustomerContractPage req) {
        return R.ok(customerContractService.queryPage(req));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加客户合同配置", notes = "添加客户合同配置")
    public R add(@RequestBody @Valid CustomerContractAdd req) {
        customerContractService.add(req);
        return R.ok(null);
    }


    @PostMapping("/updateBase")
    @ApiOperation(value = "修改客户合同配置", notes = "修改客户合同配置")
    public R updateBase(@RequestBody @Valid CustomerContractUpdate req) {
        customerContractService.updateBase(req);
        return R.ok(null);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public R delete(@RequestBody @Valid IdBase req) {
        customerContractService.delete(req.getId());
        return R.ok(null);
    }

}
