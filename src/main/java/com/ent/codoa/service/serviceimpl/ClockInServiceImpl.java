package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TimeTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.AttendanceSetting;
import com.ent.codoa.entity.ClockIn;
import com.ent.codoa.mapper.ClockInMapper;
import com.ent.codoa.pojo.req.clockin.ClockInStaffQuery;
import com.ent.codoa.service.AttendanceSettingService;
import com.ent.codoa.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClockInServiceImpl extends ServiceImpl<ClockInMapper, ClockIn> implements ClockInService {

    @Autowired
    private AttendanceSettingService attendanceSettingService;

    @Override
    public List<ClockIn> findByMonthAndAccount(ClockInStaffQuery dto) {
        QueryWrapper<ClockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(ClockIn::getAccount, dto.getAccount())
            .likeRight(ClockIn::getStartTime, dto.getMonth())
            .orderByDesc(ClockIn::getStartTime);
        return list(queryWrapper);
    }

    /**
     * 转化打卡状态
     *
     * @param attendanceSetting
     * @param clockIn
     * @return
     */
    private void convertStatus(AttendanceSetting attendanceSetting, ClockIn clockIn) {
        //如果是刚上班打卡
        if (clockIn.getEndTime() == null) {
            if (clockIn.getStartTime().compareTo(TimeTools.getTodayDateTime(attendanceSetting.getStartWorkTime())) <= 0) {
                clockIn.setStatus("正常");
            } else {
                clockIn.setStatus("迟到打卡");
            }
        } else {
            //如果是下班打卡
            if (clockIn.getEndTime().compareTo(TimeTools.getTodayDateTime(attendanceSetting.getEndWorkTime())) >= 0) {
                clockIn.setStatus("正常");
            } else {
                clockIn.setStatus("早退打卡");
            }
        }

    }

    @Override
    public void add() {
        //查出来上班和下班时间配置
        AttendanceSetting attendanceSetting = attendanceSettingService.clientFindBySystemClientAccount();
        //查出今天时间
        LocalDateTime today = LocalDateTime.now();
        QueryWrapper<ClockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().likeRight(ClockIn::getStartTime, today.toLocalDate());
        ClockIn clockIn = getOne(queryWrapper);
        //没有打卡就新增
        if (clockIn == null) {
            clockIn = new ClockIn();
            clockIn.setStartTime(LocalDateTime.now());
            clockIn.setAccount(TokenTools.getAccount());
            clockIn.setSystemClientAccount(TokenTools.getSystemClientAccount());
            convertStatus(attendanceSetting, clockIn);
            save(clockIn);
        } else {
            //打卡了就更新下班时间和状态
            clockIn.setEndTime(LocalDateTime.now());
            convertStatus(attendanceSetting, clockIn);
            updateById(clockIn);
        }
    }

    @Override
    public void delete(Long id) {
        removeById(id);

        LogTools.addLog("考勤管理", "删除了一条打开记录:" + JSONUtil.toJsonStr(getById(id)), TokenTools.getLoginToken(true));
    }

    @Override
    public List<ClockIn> staffQuery(String date) {
        QueryWrapper<ClockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(ClockIn::getAccount, TokenTools.getAccount())
            .likeRight(ClockIn::getStartTime, date)
            .orderByDesc(ClockIn::getStartTime);
        return list(queryWrapper);
    }

}
