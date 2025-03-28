package com.ent.codoa.controller.client;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.entity.ClockIn;
import com.ent.codoa.pojo.req.MonthBase;
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
@Api(tags = "人事管理-考勤管理")
@RequestMapping("/client/clockIn")
public class ClockInApi {

    @Autowired
    private ClockInService clockInService;

    @PostMapping("/findByMonth")
    @ApiOperation(value = "查询某月份记录", notes = "查询某月份记录")
    public R<List<ClockIn>> findByMonth(@RequestBody @Valid MonthBase req) {
        List<ClockIn> list = clockInService.staffQuery(req.getMonth());
        return R.ok(list);
    }

    @PostMapping("/clockIn")
    @ApiOperation(value = "打卡", notes = "打卡")
    public R clockIn() {
        clockInService.add();
        return R.ok(null);
    }

}
