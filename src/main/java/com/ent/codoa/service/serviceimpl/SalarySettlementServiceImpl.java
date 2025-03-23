package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TimeTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.*;
import com.ent.codoa.mapper.SalarySettlementMapper;
import com.ent.codoa.pojo.dto.clockin.ClockInCheckDto;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.clockin.ClockInStaffQuery;
import com.ent.codoa.pojo.req.salarysettlement.SalarySettlementAdd;
import com.ent.codoa.pojo.req.salarysettlement.SalarySettlementPage;
import com.ent.codoa.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class SalarySettlementServiceImpl extends ServiceImpl<SalarySettlementMapper, SalarySettlement> implements SalarySettlementService {

    @Autowired
    private StaffService staffService;
    @Autowired
    private ClockInService clockInService;
    @Autowired
    private PerformanceAppraisalService performanceAppraisalService;
    @Autowired
    private AttendanceSettingService attendanceSettingService;

    @Override
    public IPage<SalarySettlement> queryPage(SalarySettlementPage dto) {
        IPage<SalarySettlement> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<SalarySettlement> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getAccount()), SalarySettlement::getAccount, dto.getAccount())
            .eq(StringUtils.isNotBlank(dto.getDate()), SalarySettlement::getDate, dto.getDate())
            .eq(SalarySettlement::getSystemClientAccount, TokenTools.getAccount())
            .orderByDesc(SalarySettlement::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void add(SalarySettlementAdd dto) {
        SalarySettlement salarySettlement = BeanUtil.toBean(dto, SalarySettlement.class);
        salarySettlement.setSystemClientAccount(TokenTools.getAccount());
        salarySettlement.setCreateName(TokenTools.getName());
        salarySettlement.setCreateTime(LocalDateTime.now());

        Staff staff = staffService.findByAccount(dto.getAccount());
        salarySettlement.setBaseSalary(new BigDecimal(staff.getSalary()));

        PerformanceAppraisal performanceAppraisal = performanceAppraisalService.findByDateAndAccount(dto.getDate(), dto.getAccount());
        if (performanceAppraisal != null) {
            salarySettlement.setPerformanceScore(performanceAppraisal.getLevel());
        }

        save(salarySettlement);

        LogTools.addLog("薪资结算", "新增一条结算薪资:" + JSONUtil.toJsonStr(salarySettlement), TokenTools.getLoginToken(true));
    }

    @Override
    public void delete(Long id) {
        removeById(id);
        LogTools.addLog("薪资结算", "删除一条结算薪资记录:" + id, TokenTools.getLoginToken(true));
    }

    @Override
    public IPage<SalarySettlement> clientPage(PageBase dto) {
        IPage<SalarySettlement> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<SalarySettlement> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(SalarySettlement::getAccount, TokenTools.getAccount())
            .orderByDesc(SalarySettlement::getCreateTime);
        return page(iPage, queryWrapper);
    }

    //根据上班打卡时间 和下班打卡时间 解析当天迟到,加班,出勤等信息
    private ClockInCheckDto checkClockInTime(LocalDateTime clockInStartTime, LocalDateTime clockInEndTime, String startWorkTime, String endWorkTime) {
        ClockInCheckDto clockInCheckDto = new ClockInCheckDto();
        LocalDateTime todayStartTime = TimeTools.getDateTime(clockInStartTime.toLocalDate(), startWorkTime);
        LocalDateTime todayEndTime = TimeTools.getDateTime(clockInEndTime.toLocalDate(), endWorkTime);

        //如果当天打卡了
        if (todayStartTime.toLocalDate().compareTo(clockInStartTime.toLocalDate()) == 0) {
            //下班之前打上班卡了,并且 上班之前没有打下班卡 就算出勤了
            if (todayEndTime.compareTo(clockInStartTime) < 0 && todayStartTime.compareTo(clockInEndTime) < 0) {
                clockInCheckDto.setActualAttendance(new BigDecimal(1));

                //判断出勤了,但是迟到了 并且不是六日
                DayOfWeek dayOfWeek = clockInStartTime.toLocalDate().getDayOfWeek();
                if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                    if (todayStartTime.compareTo(clockInStartTime) < 0) {
                        long lateMinutes = Duration.between(todayStartTime, clockInStartTime).toMinutes();
                        clockInCheckDto.setLateHours(new BigDecimal(lateMinutes).divide(new BigDecimal(60), 1, RoundingMode.HALF_UP));
                    }

                    //判断出勤了,但是早退了 并且不是六日
                    if (todayEndTime.compareTo(clockInEndTime) > 0) {
                        long leaveEarlyMinutes = Duration.between(clockInEndTime, todayEndTime).toMinutes();
                        clockInCheckDto.setLeaveEarly(new BigDecimal(leaveEarlyMinutes).divide(new BigDecimal(60), 1, RoundingMode.HALF_UP));
                    }

                    //判断今天是否加了班
                    if (todayEndTime.compareTo(clockInEndTime) < 0) {
                        long overtime = Duration.between(todayEndTime, clockInEndTime).toMinutes();
                        //如果加班30分钟以上 则开始累计加班时常
                        if (overtime > 30) {
                            //平日
                            clockInCheckDto.setWeekdaysOvertime(new BigDecimal(overtime).divide(new BigDecimal(60), 1, RoundingMode.HALF_UP));
                        }
                    }
                } else {
                    //六日
                    long overtime = Duration.between(clockInStartTime, clockInEndTime).toMinutes();
                    //六日加班也是30分钟以上 开始累计算加班
                    if (overtime > 30) {
                        clockInCheckDto.setWeekendsOvertime(new BigDecimal(overtime).divide(new BigDecimal(60), 1, RoundingMode.HALF_UP));
                    }
                }
            }
        }

        return clockInCheckDto;
    }

    @Override
    public SalarySettlement statisticsByDateAndAccount(String date, String account) {
        SalarySettlement salarySettlement = new SalarySettlement();
        salarySettlement.setDate(date);
        salarySettlement.setAccount(account);

        //查询员工基本福利属性
        Staff staff = staffService.findByAccount(account);
        if (staff == null) {
            throw new DataException("员工不存在");
        }
        salarySettlement.setBaseSalary(new BigDecimal(staff.getSalary()));

        if (staff.getWeekdayOvertimePayRate() == null || staff.getWeekendOvertimePayRate() == null) {
            throw new DataException("请先配置该员工档案中的加班费用比例数据");
        }
        salarySettlement.setWeekdayOvertimePayRate(new BigDecimal(staff.getWeekdayOvertimePayRate()));
        salarySettlement.setWeekendOvertimePayRate(new BigDecimal(staff.getWeekendOvertimePayRate()));

        if (staff.getPayTaxesRate() == null) {
            throw new DataException("请先配置该员工档案中的交税比例数据");
        }
        salarySettlement.setPayTaxesRate(staff.getPayTaxesRate());

        //查询查询考勤上班时间设置
        AttendanceSetting attendanceSetting = attendanceSettingService.findBySCA(TokenTools.getAccount());
        if (attendanceSetting == null) {
            throw new DataException("缺少上班打开时间配置");
        }

        String[] dateArray = date.split("-");
        Set<LocalDate> localDateSet = TimeTools.getWorkdaysSet(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]));

        //查询员工打卡出勤情况

        //查询打卡记录
        ClockInStaffQuery clockInStaffQuery = new ClockInStaffQuery();
        clockInStaffQuery.setAccount(account);
        clockInStaffQuery.setMonth(date);
        List<ClockIn> clockInList = clockInService.findByMonthAndAccount(clockInStaffQuery);
        if (CollectionUtils.isEmpty(clockInList)) {
            throw new DataException(date + "无出勤");
        }

        //实际出勤天数
        BigDecimal actualAttendance = BigDecimal.ZERO;
        //迟到时间
        BigDecimal lateHours = BigDecimal.ZERO;
        //早退时间
        BigDecimal leaveEarly = BigDecimal.ZERO;
        //平日加班时间
        BigDecimal weekdaysOvertime = BigDecimal.ZERO;
        //六日加班时间
        BigDecimal weekendsOvertime = BigDecimal.ZERO;
        //遍历打卡记录
        for (ClockIn clockIn : clockInList) {
            //如果出勤日,包括了打卡日 并且 打卡时间在上班时间之前与下班时间之间 就算出勤了 后面如果有早退或者迟到另算就好
            if (localDateSet.contains(clockIn.getStartTime().toLocalDate())) {
                ClockInCheckDto clockInCheckDto = checkClockInTime(clockIn.getStartTime(), clockIn.getEndTime(), attendanceSetting.getStartWorkTime(), attendanceSetting.getEndWorkTime());
                actualAttendance = actualAttendance.add(clockInCheckDto.getActualAttendance());
                lateHours = lateHours.add(clockInCheckDto.getLateHours());
                leaveEarly = leaveEarly.add(clockInCheckDto.getLeaveEarly());
                weekdaysOvertime = weekdaysOvertime.add(clockInCheckDto.getWeekdaysOvertime());
                weekendsOvertime = weekendsOvertime.add(clockInCheckDto.getWeekendsOvertime());
            }
        }
        salarySettlement.setExpectedAttendance(new BigDecimal(localDateSet.size()).setScale(0, RoundingMode.DOWN));

        //统计出来的迟到,早退,加班等数据存入对象
        salarySettlement.setActualAttendance(actualAttendance);
        salarySettlement.setAbsenceDays(salarySettlement.getExpectedAttendance().subtract(salarySettlement.getActualAttendance()));
        salarySettlement.setLateHours(lateHours);
        salarySettlement.setLeaveEarly(leaveEarly);
        salarySettlement.setWeekdaysOverTime(weekdaysOvertime);
        salarySettlement.setWeekendsOverTime(weekendsOvertime);

        //绩效
        PerformanceAppraisal performanceAppraisal = performanceAppraisalService.findByDateAndAccount(date, account);
        if (performanceAppraisal != null) {
            salarySettlement.setPerformanceScore(performanceAppraisal.getLevel());
        }

        //计算工资 ( 基本工资 / 应该出勤的天数 * 实际出勤天数 + 平日加班费 + 六日加班费 ) as 税前工作工资 - 税支出
        //日薪资
        BigDecimal daySalary = salarySettlement.getBaseSalary().divide(salarySettlement.getExpectedAttendance(), RoundingMode.DOWN);
        //无加班工资
        BigDecimal estimateSalary = daySalary.multiply(salarySettlement.getActualAttendance());
        //平日加班费
        salarySettlement.setWeekdayOvertimeAmount(salarySettlement.getWeekdayOvertimePayRate().multiply(salarySettlement.getWeekdaysOverTime()));
        //六日加班费
        salarySettlement.setWeekendOvertimeAmount(salarySettlement.getWeekendOvertimePayRate().multiply(salarySettlement.getWeekendsOverTime()));
        //加上平日加班费 加上六日加班费
        salarySettlement.setEstimateSalary(estimateSalary.add(salarySettlement.getWeekdayOvertimeAmount()).add(salarySettlement.getWeekendOvertimeAmount()));
        //税
        salarySettlement.setPayTaxesAmount(salarySettlement.getEstimateSalary().divide(new BigDecimal(salarySettlement.getPayTaxesRate()), 2, RoundingMode.DOWN));
        //减去税
        salarySettlement.setEstimateSalary(salarySettlement.getEstimateSalary().subtract(salarySettlement.getPayTaxesAmount()));
        return salarySettlement;
    }

    @Override
    public SalarySettlement specifyDataStatistics(SalarySettlementAdd dto) {

        return null;
    }


}
