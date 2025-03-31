package com.ent.codoa.common.tools;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;

/**
 * 时间工具类
 */
@Slf4j
public class TimeTools {

    /**
     * 获取本周开始时间
     *
     * @param now
     * @return
     */
    public static LocalDateTime thisWeekStartTime(LocalDateTime now) {
        // 计算本周的开始和结束时间（从周一到周日）
        return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .withHour(0).withMinute(0).withSecond(0);
    }

    /**
     * 获取本周的结束时间
     *
     * @param now
     * @return
     */
    public static LocalDateTime thisWeekEndTime(LocalDateTime now) {
        // 计算本周的开始和结束时间（从周一到周日）
        return now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .withHour(23).withMinute(59).withSecond(59);
    }

    /**
     * 判断是否在本周时间以内
     *
     * @param time
     * @return
     */
    public static boolean isThisWeekBetween(LocalDateTime time) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (thisWeekStartTime(currentTime).compareTo(time) <= 0 && thisWeekEndTime(currentTime).compareTo(time) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是上周时间以内
     *
     * @return
     */
    public static boolean isLastWeekBetween(LocalDateTime time) {
        LocalDateTime lastWeekTime = LocalDateTime.now().minusWeeks(1);
        if (thisWeekStartTime(lastWeekTime).minusWeeks(1).compareTo(time) <= 0 && thisWeekEndTime(lastWeekTime).minusWeeks(1).compareTo(time) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据时间转化成 当天localDateTime
     *
     * @param clock
     * @return
     */
    public static LocalDateTime getTodayDateTime(String clock) {
        LocalTime localTime = LocalTime.parse(clock);
        return LocalDateTime.of(LocalDate.now(), localTime);
    }

    /**
     * 根据月份 和 时间点 拼成时间
     * @param date
     * @param clock
     * @return
     */
    public static LocalDateTime getDateTime(LocalDate date, String clock){
        LocalTime localTime = LocalTime.parse(clock);
        return LocalDateTime.of(date, localTime);
    }


    /**
     * 获取指定年月 没有六日的出勤天数
     * @param year
     * @param month
     * @return
     */
    public static Set<LocalDate> getWorkdaysSet(int year, int month){
        YearMonth ym = YearMonth.of(year, month);
        Set<LocalDate> set = new HashSet<>();
        for (int day = 1; day <= ym.lengthOfMonth(); day++) {
            LocalDate date = ym.atDay(day);
            DayOfWeek dow = date.getDayOfWeek();
            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
                set.add(date);
            }
        }
        return set;
    }


    /**
     * 获取指定年月 没有六日的出勤天数
     * @param date
     * @return
     */
    public static Set<LocalDate> getWorkdaysSet(LocalDate date){
        return getWorkdaysSet(date.getYear(), date.getMonthValue());
    }


}
