package com.ent.codoa.controller.client;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Complaint;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.complaint.ComplaintAdd;
import com.ent.codoa.pojo.req.complaint.ComplaintPage;
import com.ent.codoa.pojo.req.complaint.ComplaintStatusUpdate;
import com.ent.codoa.service.ComplaintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@Api(tags = "客户管理-投诉记录")
@RequestMapping("/client/complaint")
public class ComplaintApi {

    @Autowired
    private ComplaintService complaintService;


    @PostMapping("/page")
    @ApiOperation(value = "分页客户自己投诉记录", notes = "分页客户自己投诉记录")
    public R<IPage<Complaint>> page(@RequestBody PageBase req) {
        IPage<Complaint> iPage = complaintService.clientPage(req);
        return R.ok(iPage);
    }


    @PostMapping("/add")
    @ApiOperation(value = "新增投诉", notes = "新增投诉")
    public R add(@RequestBody ComplaintAdd req) {
        Complaint complaint = BeanUtil.toBean(req, Complaint.class);
        complaint.setAccount(TokenTools.getAccount());
        complaintService.add(complaint);
        return R.ok(null);
    }

}
