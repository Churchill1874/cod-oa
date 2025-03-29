package com.ent.codoa.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.entity.PotentialClient;
import com.ent.codoa.entity.Staff;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.potentialclent.PotentialClientAdd;
import com.ent.codoa.pojo.req.potentialclent.PotentialClientPage;
import com.ent.codoa.pojo.req.potentialclent.PotentialClientUpdate;
import com.ent.codoa.pojo.req.staff.StaffPage;
import com.ent.codoa.service.PotentialClientService;
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
@Api(tags = "客户管理-潜在客户")
@RequestMapping("/admin/potentialClient")
public class PotentialClientController {

    @Autowired
    private PotentialClientService potentialClientService;

    @LoginCheck
    @PostMapping("/page")
    @ApiOperation(value = "分页潜在客户", notes = "分页潜在客户")
    public R<IPage<PotentialClient>> page(@RequestBody PotentialClientPage req) {
        IPage<PotentialClient> iPage = potentialClientService.queryPage(req);
        return R.ok(iPage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加潜在客户", notes = "添加潜在客户")
    public R add(@RequestBody @Valid PotentialClientAdd req) {
        PotentialClient potentialClient = BeanUtil.toBean(req, PotentialClient.class);
        potentialClientService.add(potentialClient);
        return R.ok(null);
    }

    @PostMapping("/updateInfo")
    @ApiOperation(value = "修改潜在客户", notes = "修改潜在客户")
    public R updateInfo(@RequestBody @Valid PotentialClientUpdate req) {
        PotentialClient potentialClient = BeanUtil.toBean(req, PotentialClient.class);
        potentialClientService.updateInfo(potentialClient);
        return R.ok(null);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除潜在客户", notes = "删除潜在客户")
    public R delete(@RequestBody @Valid IdBase req) {
        potentialClientService.delete(req.getId());
        return R.ok(null);
    }



}
