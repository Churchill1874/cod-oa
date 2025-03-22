package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.LeaveManagement;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.leavemanagement.LeaveManagementAdd;
import com.ent.codoa.pojo.req.leavemanagement.LeaveManagementPage;

public interface LeaveManagementService extends IService<LeaveManagement> {

    IPage<LeaveManagement> queryPage(LeaveManagementPage dto);

    IPage<LeaveManagement> clientPage(PageBase dto);

    void add(LeaveManagementAdd dto);

    void delete(Long id);

    void approval(Long id, String status);

}
