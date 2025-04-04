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
import java.time.*;
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
        // 计算出来工资
        SalarySettlement salarySettlement = specifyDataStatistics(dto);
        salarySettlement.setSystemClientAccount(TokenTools.getAccount());
        salarySettlement.setCreateName(TokenTools.getName());
        salarySettlement.setCreateTime(LocalDateTime.now());

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
        //如果没有打下班卡 就算没出勤
        if (clockInEndTime == null){
            return clockInCheckDto;
        }

        LocalDateTime todayStartTime = TimeTools.getDateTime(clockInStartTime.toLocalDate(), startWorkTime);
        LocalDateTime todayEndTime = TimeTools.getDateTime(clockInStartTime.toLocalDate(), endWorkTime);

        //如果当天打卡了
        if (todayStartTime.toLocalDate().compareTo(clockInStartTime.toLocalDate()) == 0) {
            //下班之前打上班卡了,并且 上班之前没有打下班卡 就算出勤了
            if (todayEndTime.compareTo(clockInStartTime) > 0 && todayStartTime.compareTo(clockInEndTime) < 0) {
                clockInCheckDto.setActualAttendance(new BigDecimal(1));
                //出勤时间统计
                attendanceTimeCount(todayStartTime, todayEndTime, clockInStartTime, clockInEndTime, clockInCheckDto);
            }
        }

        return clockInCheckDto;
    }


    //出勤时间统计
    private void attendanceTimeCount(LocalDateTime todayStartTime, LocalDateTime todayEndTime,
                                     LocalDateTime clockInStartTime, LocalDateTime clockInEndTime,
                                     ClockInCheckDto clockInCheckDto){

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


    @Override
    public SalarySettlement statisticsByDateAndAccount(String date, String account) {
        SalarySettlement salarySettlement = new SalarySettlement();
        salarySettlement.setDate(date);
        salarySettlement.setAccount(account);

        //查询员工基本福利属性
        Staff staff = staffService.findByAccount(account);
        if (staff == null) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("员工不存在");
            }else{
                throw new DataException("該当する社員が存在しません");
            }
        }
        salarySettlement.setBaseSalary(new BigDecimal(staff.getSalary()));

        if (staff.getWeekdayOvertimePayRate() == null || staff.getWeekendOvertimePayRate() == null) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("请先配置该员工档案中的加班费用比例数据");
            }else{
                throw new DataException("当該社員の残業単価設定が未登録です。人事システムで設定してください");
            }
        }
        salarySettlement.setWeekdayOvertimePayRate(staff.getWeekdayOvertimePayRate());
        salarySettlement.setWeekendOvertimePayRate(staff.getWeekendOvertimePayRate());

        if (staff.getPayTaxesRate() == null) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("请先配置该员工档案中的交税比例数据");
            }else{
                throw new DataException("当該社員の所得税率設定が未登録です。給与システムで設定してください");
            }
        }
        salarySettlement.setPayTaxesRate(staff.getPayTaxesRate());

        //查询查询考勤上班时间设置
        AttendanceSetting attendanceSetting = attendanceSettingService.findBySCA(TokenTools.getAccount());
        if (attendanceSetting == null) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("缺少上班打开时间配置");
            }else{
                throw new DataException("出勤打刻時刻の設定が不足しています");
            }
        }

        String[] dateArray = date.split("-");
        Set<LocalDate> localDateSet = TimeTools.getWorkdaysSet(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]));
        salarySettlement.setExpectedAttendance(new BigDecimal(localDateSet.size()).setScale(0, RoundingMode.DOWN));

        //查询打卡记录
        ClockInStaffQuery clockInStaffQuery = new ClockInStaffQuery();
        clockInStaffQuery.setAccount(account);
        clockInStaffQuery.setMonth(date);
        List<ClockIn> clockInList = clockInService.findByMonthAndAccount(clockInStaffQuery);
        if (CollectionUtils.isEmpty(clockInList)) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException(date + "无出勤");
            }else{
                throw new DataException(date + "出勤なし");
            }

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


        //统计出来的迟到,早退,加班等数据存入对象
        salarySettlement.setActualAttendance(actualAttendance);
        salarySettlement.setAbsenceDays(salarySettlement.getExpectedAttendance().subtract(salarySettlement.getActualAttendance()));
        salarySettlement.setLateHours(lateHours);
        salarySettlement.setLeaveEarly(leaveEarly);
        salarySettlement.setWeekdaysOvertime(weekdaysOvertime);
        salarySettlement.setWeekendsOvertime(weekendsOvertime);

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
        //每天工作时常 小时
        long hours = Duration.between(LocalTime.parse(attendanceSetting.getStartWorkTime()),LocalTime.parse(attendanceSetting.getEndWorkTime())).toHours();
        //每小时的基本工资
        BigDecimal hourSalary = daySalary.divide(new BigDecimal(hours), 2, RoundingMode.DOWN);
        salarySettlement.setWeekdayOvertimeAmount(hourSalary.multiply(salarySettlement.getWeekdaysOvertime()).multiply(salarySettlement.getWeekdayOvertimePayRate()));
        //六日加班费
        salarySettlement.setWeekendOvertimeAmount(hourSalary.multiply(salarySettlement.getWeekendsOvertime()).multiply(salarySettlement.getWeekendOvertimePayRate()));
        //加上平日加班费 加上六日加班费
        salarySettlement.setEstimateSalary(estimateSalary.add(salarySettlement.getWeekdayOvertimeAmount()).add(salarySettlement.getWeekendOvertimeAmount()));
        //税
        //税的比例
        BigDecimal payTaxesRate = new BigDecimal(salarySettlement.getPayTaxesRate()).divide(new BigDecimal("100"), 2,RoundingMode.DOWN);
        salarySettlement.setPayTaxesAmount(salarySettlement.getEstimateSalary().multiply(payTaxesRate).setScale(2, RoundingMode.DOWN));
        //减去税
        salarySettlement.setEstimateSalary(salarySettlement.getEstimateSalary().subtract(salarySettlement.getPayTaxesAmount()));
        return salarySettlement;
    }

    @Override
    public SalarySettlement specifyDataStatistics(SalarySettlementAdd dto) {
        //查询查询考勤上班时间设置
        AttendanceSetting attendanceSetting = attendanceSettingService.findBySCA(TokenTools.getAccount());
        if (attendanceSetting == null) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("缺少上班打开时间配置");
            }else{
                throw new DataException("出勤打刻時刻の設定が不足しています");
            }
        }

        Staff staff = staffService.findByAccount(dto.getAccount());
        if (staff == null) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("员工不存在");
            }else{
                throw new DataException("該当する社員が存在しません");
            }
        }
        if (staff.getSalary() == null) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("员工基本工资未配置");
            }else{
                throw new DataException("当該社員の基本給が未設定です");
            }

        }
        SalarySettlement salarySettlement = BeanUtil.toBean(dto, SalarySettlement.class);
        salarySettlement.setBaseSalary(new BigDecimal(staff.getSalary()));

        //计算工资 ( 基本工资 / 应该出勤的天数 * 实际出勤天数 + 平日加班费 + 六日加班费 ) as 税前工作工资 - 税支出
        //日薪资
        BigDecimal daySalary = salarySettlement.getBaseSalary().divide(salarySettlement.getExpectedAttendance(), RoundingMode.DOWN);
        //无加班工资
        BigDecimal estimateSalary = daySalary.multiply(salarySettlement.getActualAttendance());
        //平日加班费
        //每天工作时常 小时
        long hours = Duration.between(LocalTime.parse(attendanceSetting.getStartWorkTime()),LocalTime.parse(attendanceSetting.getEndWorkTime())).toHours();
        //每小时的基本工资
        BigDecimal hourSalary = daySalary.divide(new BigDecimal(hours), 2, RoundingMode.DOWN);
        salarySettlement.setWeekdayOvertimeAmount(hourSalary.multiply(salarySettlement.getWeekdaysOvertime()).multiply(salarySettlement.getWeekdayOvertimePayRate()));
        //六日加班费
        salarySettlement.setWeekendOvertimeAmount(hourSalary.multiply(salarySettlement.getWeekendsOvertime()).multiply(salarySettlement.getWeekendOvertimePayRate()));
        //加上平日加班费 加上六日加班费
        salarySettlement.setEstimateSalary(estimateSalary.add(salarySettlement.getWeekdayOvertimeAmount()).add(salarySettlement.getWeekendOvertimeAmount()));
        //税
        BigDecimal payTaxesRate = new BigDecimal(salarySettlement.getPayTaxesRate()).divide(new BigDecimal("100"), 2,RoundingMode.DOWN);
        salarySettlement.setPayTaxesAmount(salarySettlement.getEstimateSalary().multiply(payTaxesRate).setScale(2, RoundingMode.DOWN));
        //减去税
        salarySettlement.setEstimateSalary(salarySettlement.getEstimateSalary().subtract(salarySettlement.getPayTaxesAmount()));
        return salarySettlement;
    }


}
