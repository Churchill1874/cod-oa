package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.common.annotation.StaffAuthCheck;
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

    @StaffAuthCheck
    @PostMapping("/findBySCA")
    @ApiOperation(value = "查询考勤配置", notes = "查询考勤配置")
    public R<AttendanceSetting> findBySCA() {
        AttendanceSetting attendanceSetting = attendanceSettingService.findBySCA(TokenTools.getAccount());
        return R.ok(attendanceSetting);
    }

    @StaffAuthCheck
    @PostMapping("/setting")
    @ApiOperation(value = "考勤设置", notes = "考勤设置")
    public R setting(@RequestBody @Valid AttendanceSettingUpdate req) {
        String[] startTimeArray = req.getStartWork().split(":");
        if (Integer.parseInt(startTimeArray[0]) < 10 && !startTimeArray[0].contains("0")){
            req.setStartWork("0" + startTimeArray[0] + ":" + startTimeArray[1]);
        }

        String[] endTimeArray = req.getEndWork().split(":");
        if (Integer.parseInt(endTimeArray[0]) < 10 && !endTimeArray[0].contains("0")){
            req.setEndWork("0" + endTimeArray[0] + ":" + endTimeArray[1]);
        }
        attendanceSettingService.setting(req);
        return R.ok(null);
    }

}
