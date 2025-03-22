package com.ent.codoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.PerformanceAppraisalSetting;
import com.ent.codoa.pojo.req.performanceappraisalsetting.PerformanceAppraisalSettingAdd;

public interface PerformanceAppraisalSettingService extends IService<PerformanceAppraisalSetting> {

    void setting(PerformanceAppraisalSettingAdd dto);

    PerformanceAppraisalSetting findBySystemClientAccount();

}
