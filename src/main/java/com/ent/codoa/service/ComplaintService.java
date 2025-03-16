package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Complaint;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.pojo.req.complaint.ComplaintPage;
import com.ent.codoa.pojo.req.complaint.ComplaintStatusUpdate;

import java.util.List;

public interface ComplaintService extends IService<Complaint> {

    IPage<Complaint> queryPage(ComplaintPage dto);

    void add(Complaint dto);

    void updateStatus(ComplaintStatusUpdate dto);

    /**
     * 查询近三个月的数据
     * @return
     */
    List<Complaint> withinThreeMonth();
}
