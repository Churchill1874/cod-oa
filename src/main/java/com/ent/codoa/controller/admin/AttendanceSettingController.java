package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.AttendanceSetting;
import com.ent.codoa.pojo.req.attendancesetting.AttendanceSettingUpdate;
import com.ent.codoa.service.AttendanceSettingService;
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
@Api(tags = "人事管理-考勤配置")
@RequestMapping("/admin/attendanceSetting")
public class AttendanceSettingController {

    @Autowired
    private AttendanceSettingService attendanceSettingService;

    @PostMapping("/findBySCA")
    @ApiOperation(value = "查询考勤配置", notes = "查询考勤配置")
    public R<AttendanceSetting> findBySCA() {
        AttendanceSetting attendanceSetting = attendanceSettingService.findBySCA(TokenTools.getAccount());
        return R.ok(attendanceSetting);
    }

    @PostMapping("/setting")
    @ApiOperation(value = "考勤设置", notes = "考勤设置")
    public R setting(@RequestBody @Valid AttendanceSettingUpdate req) {
        attendanceSettingService.setting(req);
        return R.ok(null);
    }

}
