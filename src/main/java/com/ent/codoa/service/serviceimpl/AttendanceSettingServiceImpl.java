package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.AttendanceSetting;
import com.ent.codoa.mapper.AttendanceSettingMapper;
import com.ent.codoa.pojo.req.attendancesetting.AttendanceSettingAdd;
import com.ent.codoa.service.AttendanceSettingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AttendanceSettingServiceImpl extends ServiceImpl<AttendanceSettingMapper, AttendanceSetting> implements AttendanceSettingService {

    @Override
    public AttendanceSetting findBySCA(String systemClientAccount) {
        QueryWrapper<AttendanceSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AttendanceSetting::getSystemClientAccount, systemClientAccount);
        AttendanceSetting attendanceSetting = getOne(queryWrapper);
        if (attendanceSetting == null){
            attendanceSetting = init(systemClientAccount);
        }
        return attendanceSetting;
    }

    @Override
    public void setting(AttendanceSettingAdd dto) {
        AttendanceSetting attendanceSetting = findBySCA(TokenTools.getAccount());
        if (attendanceSetting == null){
            attendanceSetting = BeanUtil.toBean(dto, AttendanceSetting.class);
            save(attendanceSetting);
        } else {
            attendanceSetting.setStartWorkTime(dto.getStartWork());
            attendanceSetting.setEndWorkTime(dto.getEndWork());
            updateById(attendanceSetting);
        }
    }

    @Override
    public AttendanceSetting init(String systemClientAccount) {
        AttendanceSetting attendanceSetting = findBySCA(systemClientAccount);
        if (attendanceSetting != null){
            return attendanceSetting;
        }
        attendanceSetting = new AttendanceSetting();
        attendanceSetting.setStartWorkTime("09:00");
        attendanceSetting.setEndWorkTime("18:00");
        attendanceSetting.setSystemClientAccount(systemClientAccount);
        attendanceSetting.setCreateTime(LocalDateTime.now());
        attendanceSetting.setCreateName(TokenTools.getName());
        save(attendanceSetting);
        return attendanceSetting;
    }

    @Override
    public AttendanceSetting clientFindBySystemClientAccount() {
        String systemClientAccount = TokenTools.getSystemClientAccount();
        return findBySCA(systemClientAccount);
    }

}
