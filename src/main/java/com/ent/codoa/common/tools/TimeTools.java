package com.ent.codoa.common.tools;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/**
 * 时间工具类
 */
public class TimeTools {

    /**
     * 获取本周开始时间
     * @param now
     * @return
     */
    public static LocalDateTime thisWeekStartTime(LocalDateTime now){
        // 计算本周的开始和结束时间（从周一到周日）
        return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .withHour(0).withMinute(0).withSecond(0);
    }

    /**
     * 获取本周的结束时间
     * @param now
     * @return
     */
    public static LocalDateTime thisWeekEndTime(LocalDateTime now){
        // 计算本周的开始和结束时间（从周一到周日）
        return now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            .withHour(23).withMinute(59).withSecond(59);
    }

    /**
     * 判断是否在本周时间以内
     * @param time
     * @return
     */
    public static boolean isThisWeekBetween(LocalDateTime time){
        if (thisWeekStartTime(time).compareTo(time) <= 0 && thisWeekEndTime(time).compareTo(time) >= 0){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是上周时间以内
     * @return
     */
    public static boolean isLastWeekBetween(LocalDateTime time){
        if (thisWeekStartTime(time).minusWeeks(1).compareTo(time) <= 0 && thisWeekEndTime(time).minusWeeks(1).compareTo(time) >= 0){
            return true;
        }
        return false;
    }

    /**
     * 根据时间转化成 当天localDateTime
     * @param clock
     * @return
     */
    public static LocalDateTime getTodayDateTime (String clock){
        LocalTime localTime = LocalTime.parse(clock);
        return LocalDateTime.of(LocalDate.now(), localTime);
    }

}
