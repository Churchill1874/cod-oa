package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.ClockIn;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.MonthBase;
import com.ent.codoa.pojo.req.clockin.ClockInStaffQuery;
import com.ent.codoa.service.ClockInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "人事管理-考勤记录")
@RequestMapping("/admin/clockIn")
public class ClockInController {

    @Autowired
    private ClockInService clockInService;

    @PostMapping("/findByMonthAndAccount")
    @ApiOperation(value = "查询某员工某月份记录", notes = "查询某员工某月份记录")
    public R<List<ClockIn>> findByMonthAndAccount(@RequestBody @Valid ClockInStaffQuery req) {
        List<ClockIn> list = clockInService.findByMonthAndAccount(req);
        return R.ok(list);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "根据id删除上下班打卡的一条记录", notes = "根据id删除上下班打卡的一条记录")
    public R delete(@RequestBody @Valid IdBase req) {
        clockInService.delete(req.getId());
        return R.ok(null);
    }

}
