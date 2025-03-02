package com.ent.codoa.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.exception.AccountOrPasswordException;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.entity.BusinessClient;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.businessclient.BusinessClientAdd;
import com.ent.codoa.pojo.req.businessclient.BusinessClientBaseUpdate;
import com.ent.codoa.pojo.req.businessclient.BusinessClientStatusUpdate;
import com.ent.codoa.service.BusinessClientService;
import com.ent.codoa.service.SystemClientService;
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
@Api(tags = "业务用户")
@RequestMapping("/admin/businessClient")
public class BusinessClientController {
    @Autowired
    private SystemClientService systemClientService;
    @Autowired
    private BusinessClientService businessClientService;

    @PostMapping("/page")
    @ApiOperation(value = "分页业务用户", notes = "分页业务用户")
    public R<IPage<BusinessClient>> page(@RequestBody PageBase req) {
        IPage<BusinessClient> iPage = businessClientService.queryPage(req);
        return R.ok(iPage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加业务用户", notes = "添加业务用户")
    public R add(@RequestBody @Valid BusinessClientAdd req) {
        if (systemClientService.findByAccount(req.getAccount()) != null
            || businessClientService.findByAccount(req.getAccount()) != null){
            throw new DataException("账号已经存在,请修改");
        }
        BusinessClient businessClient = BeanUtil.toBean(req, BusinessClient.class);
        businessClientService.add(businessClient);
        return R.ok(null);
    }

    @PostMapping("/updateBase")
    @ApiOperation(value = "添加业务用户", notes = "添加业务用户")
    public R updateBase(@RequestBody @Valid BusinessClientBaseUpdate req) {
        businessClientService.updateBaseInfo(req);
        return R.ok(null);
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改业务用户状态", notes = "修改业务用户状态")
    public R updateStatus(@RequestBody @Valid BusinessClientStatusUpdate req) {
        businessClientService.updateStatus(req.getId(), req.getStatus());
        return R.ok(null);
    }

}
