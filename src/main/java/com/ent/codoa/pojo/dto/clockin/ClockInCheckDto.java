package com.ent.codoa.pojo.dto.clockin;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClockInCheckDto {

    //实际出勤天数
    private BigDecimal actualAttendance = BigDecimal.ZERO;
    //迟到时间
    private BigDecimal lateHours = BigDecimal.ZERO;
    //早退时间
    private BigDecimal leaveEarly = BigDecimal.ZERO;
    //平日加班时间
    private BigDecimal weekdaysOvertime = BigDecimal.ZERO;
    //六日加班时间
    private BigDecimal weekendsOvertime = BigDecimal.ZERO;

}
