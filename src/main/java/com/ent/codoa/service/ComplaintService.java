package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Complaint;
import com.ent.codoa.pojo.req.complaint.ComplaintPage;
import com.ent.codoa.pojo.req.complaint.ComplaintStatusUpdate;

public interface ComplaintService extends IService<Complaint> {

    IPage<Complaint> queryPage(ComplaintPage dto);

    void add(Complaint dto);

    void updateStatus(ComplaintStatusUpdate dto);

}
