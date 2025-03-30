package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.map.TolerantMap;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TimeTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.AttendanceSetting;
import com.ent.codoa.entity.ClockIn;
import com.ent.codoa.entity.Staff;
import com.ent.codoa.mapper.ClockInMapper;
import com.ent.codoa.pojo.req.clockin.ClockInStaffQuery;
import com.ent.codoa.service.AttendanceSettingService;
import com.ent.codoa.service.ClockInService;
import com.ent.codoa.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClockInServiceImpl extends ServiceImpl<ClockInMapper, ClockIn> implements ClockInService {

    @Autowired
    private StaffService staffService;

    @Autowired
    private AttendanceSettingService attendanceSettingService;

    @Override
    public List<ClockIn> findByMonthAndAccount(ClockInStaffQuery dto) {
        String account = dto.getAccount();
        //获取该月存在的每一天
        String[] dateArray = dto.getMonth().split("-");

        //判断该指定查询月份是否 员工那时还没有入职
        Staff staff = staffService.findByAccount(account);
        if (staff != null && staff.getHireDate() != null) {
            if (Integer.parseInt(dateArray[0]) < staff.getHireDate().getYear()) {
                return null;
            }
            if (Integer.parseInt(dateArray[1]) < staff.getHireDate().getMonthValue()) {
                return null;
            }
        }

        QueryWrapper<ClockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(ClockIn::getAccount, account)
            .likeRight(ClockIn::getStartTime, dto.getMonth())
            .orderByDesc(ClockIn::getStartTime);

        List<ClockIn> list = list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        //填补出勤日记录
        fillWorkDay(list, dateArray, account, TokenTools.getAccount());

        return list;
    }

    /**
     * 转化打卡状态
     *
     * @param attendanceSetting
     * @param clockIn
     * @return
     */
    private void convertStatus(AttendanceSetting attendanceSetting, ClockIn clockIn) {
        //如果是要打下班卡
        if (clockIn.getEndTime() == null) {
            if (clockIn.getStartTime().compareTo(TimeTools.getTodayDateTime(attendanceSetting.getStartWorkTime())) <= 0) {
                clockIn.setStatus("正常");
            } else {
                clockIn.setStatus("迟到打卡");
            }
        } else {
            //如果是下班打卡
            if (clockIn.getEndTime().compareTo(TimeTools.getTodayDateTime(attendanceSetting.getEndWorkTime())) >= 0) {
                //如果上班打卡时间也大于下班时间
                if (clockIn.getStartTime().compareTo(TimeTools.getTodayDateTime(attendanceSetting.getEndWorkTime())) > 0) {
                    clockIn.setStatus("缺勤");
                }
            } else {
                if ("迟到打卡".equals(clockIn.getStatus())) {
                    clockIn.setStatus("迟到打卡/早退打卡");
                } else {
                    clockIn.setStatus("早退打卡");
                }
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
        queryWrapper.lambda()
            .eq(ClockIn::getAccount, TokenTools.getAccount())
            .likeRight(ClockIn::getStartTime, today.toLocalDate());
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
        String account = TokenTools.getAccount();
        //获取该月存在的每一天
        String[] dateArray = date.split("-");

        //判断该指定查询月份是否 员工那时还没有入职
        Staff staff = staffService.findByAccount(account);
        if (staff != null && staff.getHireDate() != null) {
            if (Integer.parseInt(dateArray[0]) < staff.getHireDate().getYear()) {
                return null;
            }
            if (Integer.parseInt(dateArray[1]) < staff.getHireDate().getMonthValue()) {
                return null;
            }
        }

        QueryWrapper<ClockIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(ClockIn::getAccount, account)
            .likeRight(ClockIn::getStartTime, date)
            .orderByDesc(ClockIn::getStartTime);
        List<ClockIn> list = list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        //填补出勤日记录
        fillWorkDay(list, dateArray, account, TokenTools.getSystemClientAccount());

        return list;
    }

    //填补出勤日记录
    private void fillWorkDay(List<ClockIn> list , String[] dateArray, String account, String systemClientAccount){
        //转换set 存这个月都有哪些日子打卡了
        Set<LocalDate> attendanceSet = list.stream().map(clockIn -> clockIn.getStartTime().toLocalDate()).collect(Collectors.toSet());

        //遍历这个月都有哪些工作日
        Set<LocalDate> workDaysSet = TimeTools.getWorkdaysSet(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]));
        for (LocalDate localDate : workDaysSet) {
            //如果当前的正在工作日 不存在以及打卡的记录里 那肯定缺勤了
            if (!attendanceSet.contains(localDate) && localDate.compareTo(LocalDate.now()) < 0) {
                ClockIn clockIn = new ClockIn();
                clockIn.setStartTime(TimeTools.getDateTime(localDate, "00:00"));
                clockIn.setStatus("缺勤");
                clockIn.setAccount(account);
                clockIn.setSystemClientAccount(systemClientAccount);
                list.add(clockIn);
            }
        }
    }

}
