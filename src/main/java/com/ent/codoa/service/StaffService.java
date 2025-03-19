package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Staff;
import com.ent.codoa.pojo.req.PageBase;
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

    /**
     * 更新合同
     * @param id
     * @param contract
     */
    void updateContract(Long id, String contract);

    /**
     * 即将到期的合同分页
     * @param dto
     * @return
     */
    IPage<Staff> aboutToExpirePage(PageBase dto);

}
