package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Staff;
import com.ent.codoa.pojo.req.staff.StaffBaseUpdate;
import com.ent.codoa.pojo.req.staff.StaffPage;
import com.ent.codoa.pojo.req.staff.StaffStatusUpdate;

public interface StaffService extends IService<Staff> {

    IPage<Staff> queryPage(StaffPage dto);

    void updateStatus(StaffStatusUpdate dto);

    void updateBase(StaffBaseUpdate dto);

    void add(Staff dto);

    /**
     * 根据账号查找
     * @param account
     * @return
     */
    Staff findByAccount(String account);

}
