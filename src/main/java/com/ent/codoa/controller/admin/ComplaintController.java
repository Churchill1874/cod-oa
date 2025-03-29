package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.entity.Complaint;
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

@Slf4j
@RestController
@Api(tags = "客户管理-投诉记录")
@RequestMapping("/admin/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @LoginCheck
    @PostMapping("/page")
    @ApiOperation(value = "分页投诉", notes = "分页投诉")
    public R<IPage<Complaint>> page(@RequestBody ComplaintPage req) {
        IPage<Complaint> iPage = complaintService.queryPage(req);
        return R.ok(iPage);
    }


    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改状态", notes = "修改状态")
    public R updateStatus(@RequestBody ComplaintStatusUpdate req) {
        complaintService.updateStatus(req);
        return R.ok(null);
    }

}
