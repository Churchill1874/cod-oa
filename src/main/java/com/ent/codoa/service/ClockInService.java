package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.ClockIn;
import com.ent.codoa.pojo.req.clockin.ClockInStaffQuery;

import java.util.List;

public interface ClockInService extends IService<ClockIn> {

    List<ClockIn> findByMonthAndAccount(ClockInStaffQuery dto);

    void add();

    void delete(Long id);

    List<ClockIn> staffQuery(String date);

}
