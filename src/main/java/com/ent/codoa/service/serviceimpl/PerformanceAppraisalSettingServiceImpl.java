package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.PerformanceAppraisalSetting;
import com.ent.codoa.mapper.PerformanceAppraisalSettingMapper;
import com.ent.codoa.pojo.req.performanceappraisalsetting.PerformanceAppraisalSettingAdd;
import com.ent.codoa.service.PerformanceAppraisalSettingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PerformanceAppraisalSettingServiceImpl extends ServiceImpl<PerformanceAppraisalSettingMapper, PerformanceAppraisalSetting> implements PerformanceAppraisalSettingService {

    @Override
    public void setting(PerformanceAppraisalSettingAdd dto) {
        PerformanceAppraisalSetting oldSetting = findBySystemClientAccount();

        //如果配置已经存在
        if (oldSetting == null){
            PerformanceAppraisalSetting performanceAppraisalSetting = BeanUtil.toBean(dto, PerformanceAppraisalSetting.class);
            performanceAppraisalSetting.setSystemClientAccount(TokenTools.getAccount());
            performanceAppraisalSetting.setCreateTime(LocalDateTime.now());
            performanceAppraisalSetting.setCreateName(TokenTools.getName());
            save(performanceAppraisalSetting);
        } else {
            UpdateWrapper<PerformanceAppraisalSetting> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda()
                .set(PerformanceAppraisalSetting::getAppraisalKey, dto.getAppraisalKey())
                .eq(PerformanceAppraisalSetting::getSystemClientAccount, oldSetting.getSystemClientAccount());
            update(updateWrapper);
        }

    }

    @Override
    public PerformanceAppraisalSetting findBySystemClientAccount() {
        QueryWrapper<PerformanceAppraisalSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PerformanceAppraisalSetting::getSystemClientAccount, TokenTools.getAccount());
        return getOne(queryWrapper);
    }

}
