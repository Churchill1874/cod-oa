package com.ent.codoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Staff;

public interface StaffService extends IService<Staff> {

    /**
     * 根据账号查找
     * @param account
     * @return
     */
    Staff findByAccount(String account);

}
