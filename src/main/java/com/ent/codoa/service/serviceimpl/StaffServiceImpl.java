package com.ent.codoa.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.entity.Staff;
import com.ent.codoa.mapper.StaffMapper;
import com.ent.codoa.service.StaffService;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {

    @Override
    public Staff findByAccount(String account) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Staff::getAccount, account);
        return getOne(queryWrapper);
    }


}
