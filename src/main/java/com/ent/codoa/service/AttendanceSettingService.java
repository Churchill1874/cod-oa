package com.ent.codoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.AttendanceSetting;
import com.ent.codoa.pojo.req.attendancesetting.AttendanceSettingAdd;

public interface AttendanceSettingService extends IService<AttendanceSetting> {

    /**
     * 根据系统账号上下班时间配置
     * @return
     */
    AttendanceSetting findBySCA(String systemClientAccount);

    /**
     * 更新考勤配置
     * @param dto
     */
    void setting(AttendanceSettingAdd dto);

    /**
     * 初始化考勤
     */
    AttendanceSetting init(String systemClientAccount);

    /**
     * 员工获取考勤配置
     * @return
     */
    AttendanceSetting clientFindBySystemClientAccount();

}
