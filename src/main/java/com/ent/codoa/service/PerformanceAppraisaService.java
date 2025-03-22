package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.PerformanceAppraisal;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.performanceappraisal.PerformanceAppraisalAdd;
import com.ent.codoa.pojo.req.performanceappraisal.PerformanceAppraisalPage;

public interface PerformanceAppraisaService extends IService<PerformanceAppraisal>{

    IPage<PerformanceAppraisal> queryPage(PerformanceAppraisalPage dto);

    void add(PerformanceAppraisalAdd dto);

    void delete(Long id);

    IPage<PerformanceAppraisal> clientPage(PageBase dto);

}
