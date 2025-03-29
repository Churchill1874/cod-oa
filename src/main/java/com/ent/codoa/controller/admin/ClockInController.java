package com.ent.codoa.controller.admin;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.TimeTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.ClockIn;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.MonthBase;
import com.ent.codoa.pojo.req.clockin.ClockInStaffQuery;
import com.ent.codoa.service.ClockInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@Api(tags = "人事管理-考勤记录")
@RequestMapping("/admin/clockIn")
public class ClockInController {

    @Autowired
    private ClockInService clockInService;

    @LoginCheck
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




    //todo 测试接口用后删除
    @PostMapping("/autoAddTestData")
    @ApiOperation(value = "测试接口-对某个账号添加某年月 全部 出勤打卡数据 ", notes = "测试接口-对某个账号自动添加某月全部出勤打卡数据")
    public R autoAddTestData(@RequestBody ClockInStaffQuery req) {
        if (StringUtils.isBlank(req.getMonth())){
            throw new DataException("月份不能为空");
        }
        String monthValue = req.getMonth().split("-")[1];
        if (Integer.parseInt(monthValue) < 10 && !monthValue.contains("0")){
            throw new DataException("小于10的月份请在前面补0");
        }
        if (StringUtils.isBlank(req.getAccount())){
            throw new DataException("账号不能为空");
        }

        QueryWrapper<ClockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().likeRight(ClockIn::getStartTime, req.getMonth()).eq(ClockIn::getAccount, req.getAccount());
        List<ClockIn> list = clockInService.list(queryWrapper);

        if (CollectionUtils.isNotEmpty(list)){
            throw new DataException("请先删除老数据在自动生成");
        }

        list = new ArrayList<>();

        String[] array = req.getMonth().split("-");
        Set<LocalDate> set = TimeTools.getWorkdaysSet(Integer.parseInt(array[0]),Integer.parseInt(array[1]));
        for(LocalDate value: set){

            int randomValue = RandomUtil.randomInt(1,100);

            ClockIn clockIn = new ClockIn();
            clockIn.setAccount(req.getAccount());
            clockIn.setSystemClientAccount(TokenTools.getAccount());
            if (randomValue > 3){
                clockIn.setStartTime(TimeTools.getDateTime(value, "09:00"));
                clockIn.setEndTime(TimeTools.getDateTime(value, "18:00"));
                clockIn.setStatus("正常");
            }
            if (randomValue == 3){
                clockIn.setStartTime(TimeTools.getDateTime(value, "09:10"));
                clockIn.setEndTime(TimeTools.getDateTime(value, "18:00"));
                clockIn.setStatus("迟到打卡");
            }
            if (randomValue == 2){
                clockIn.setStartTime(TimeTools.getDateTime(value, "09:00"));
                clockIn.setEndTime(TimeTools.getDateTime(value, "17:00"));
                clockIn.setStatus("早退打卡");
            }
            if (randomValue == 1){
                clockIn.setStartTime(TimeTools.getDateTime(value, "09:00"));
                clockIn.setEndTime(TimeTools.getDateTime(value, "18:00"));
                clockIn.setStatus("迟到打卡/早退打卡");
            }

            list.add(clockIn);
        }

        clockInService.saveBatch(list);
        return R.ok(list);
    }


    //todo 测试接口用后删除
    @PostMapping("/randomAddTestData")
    @ApiOperation(value = "测试接口-对某个账号某年月随机添加不完整打卡数据 ", notes = "测试接口-对某个账号某年月随机添加不完整打卡数据")
    public R randomAddTestData(@RequestBody ClockInStaffQuery req) {
        if (StringUtils.isBlank(req.getMonth())){
            throw new DataException("月份不能为空");
        }
        String monthValue = req.getMonth().split("-")[1];
        if (Integer.parseInt(monthValue) < 10 && !monthValue.contains("0")){
            throw new DataException("小于10的月份请在前面补0");
        }
        if (StringUtils.isBlank(req.getAccount())){
            throw new DataException("账号不能为空");
        }

        QueryWrapper<ClockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().likeRight(ClockIn::getStartTime, req.getMonth()).eq(ClockIn::getAccount, req.getAccount());
        List<ClockIn> list = clockInService.list(queryWrapper);

        if (CollectionUtils.isNotEmpty(list)){
            throw new DataException("请先删除老数据在自动生成");
        }

        list = new ArrayList<>();

        String[] array = req.getMonth().split("-");
        Set<LocalDate> set = TimeTools.getWorkdaysSet(Integer.parseInt(array[0]),Integer.parseInt(array[1]));

        int count = 0;
        int randomValue = RandomUtil.randomInt(10, set.size());

        for(LocalDate value: set){
            ClockIn clockIn = new ClockIn();
            clockIn.setAccount(req.getAccount());
            clockIn.setSystemClientAccount(TokenTools.getAccount());
            if (randomValue > 3){
                clockIn.setStartTime(TimeTools.getDateTime(value, "09:00"));
                clockIn.setEndTime(TimeTools.getDateTime(value, "18:00"));
                clockIn.setStatus("正常");
            }
            if (randomValue == 3){
                clockIn.setStartTime(TimeTools.getDateTime(value, "09:10"));
                clockIn.setEndTime(TimeTools.getDateTime(value, "18:00"));
                clockIn.setStatus("迟到打卡");
            }
            if (randomValue == 2){
                clockIn.setStartTime(TimeTools.getDateTime(value, "09:00"));
                clockIn.setEndTime(TimeTools.getDateTime(value, "17:00"));
                clockIn.setStatus("早退打卡");
            }
            if (randomValue == 1){
                clockIn.setStartTime(TimeTools.getDateTime(value, "09:00"));
                clockIn.setEndTime(TimeTools.getDateTime(value, "18:00"));
                clockIn.setStatus("迟到打卡/早退打卡");
            }

            list.add(clockIn);

            if (count == randomValue){
                break;
            }
            count++;
        }

        clockInService.saveBatch(list);

        return R.ok(null);
    }


    //todo 测试接口用后删除
    @PostMapping("/deleteTestData")
    @ApiOperation(value = "测试接口-删除某账号某年月份的打卡数据", notes = "测试接口-删除某账号某年月份的打卡数据")
    public R deleteTestData(@RequestBody ClockInStaffQuery req) {
        if (StringUtils.isBlank(req.getMonth())){
            throw new DataException("月份不能为空");
        }
        String monthValue = req.getMonth().split("-")[1];
        if (Integer.parseInt(monthValue) < 10 && !monthValue.contains("0")){
            throw new DataException("小于10的月份请在前面补0");
        }
        if (StringUtils.isBlank(req.getAccount())){
            throw new DataException("账号不能为空");
        }

        QueryWrapper<ClockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClockIn::getAccount, req.getAccount()).likeRight(ClockIn::getStartTime, req.getMonth());
        clockInService.remove(queryWrapper);
        return R.ok(null);
    }



}
