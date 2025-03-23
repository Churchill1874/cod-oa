package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.PerformanceAppraisal;
import com.ent.codoa.mapper.PerformanceAppraisalMapper;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.performanceappraisal.PerformanceAppraisalAdd;
import com.ent.codoa.pojo.req.performanceappraisal.PerformanceAppraisalPage;
import com.ent.codoa.service.PerformanceAppraisalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PerformanceAppraisalServiceImpl extends ServiceImpl<PerformanceAppraisalMapper, PerformanceAppraisal> implements PerformanceAppraisalService {

    @Override
    public IPage<PerformanceAppraisal> queryPage(PerformanceAppraisalPage dto) {
        IPage<PerformanceAppraisal> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<PerformanceAppraisal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getAccount()), PerformanceAppraisal::getAccount, dto.getAccount())
            .eq(PerformanceAppraisal::getSystemClientAccount, TokenTools.getAccount())
            .orderByDesc(PerformanceAppraisal::getCreateTime);

        return page(iPage, queryWrapper);
    }

    @Override
    public void add(PerformanceAppraisalAdd dto) {
        PerformanceAppraisal performanceAppraisal = BeanUtil.toBean(dto, PerformanceAppraisal.class);
        performanceAppraisal.setCreateName(TokenTools.getName());
        performanceAppraisal.setCreateTime(LocalDateTime.now());
        performanceAppraisal.setSystemClientAccount(TokenTools.getAccount());
        save(performanceAppraisal);

        LogTools.addLog("绩效考核","新增考核绩效:" + JSONUtil.toJsonStr(performanceAppraisal),TokenTools.getLoginToken(true));
    }

    @Override
    public void delete(Long id) {
        removeById(id);
        LogTools.addLog("绩效考核","删除考核绩效:" + id,TokenTools.getLoginToken(true));
    }

    @Override
    public IPage<PerformanceAppraisal> clientPage(PageBase dto) {
        IPage<PerformanceAppraisal> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<PerformanceAppraisal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(PerformanceAppraisal::getAccount, TokenTools.getAccount())
            .orderByDesc(PerformanceAppraisal::getCreateTime);
        return page(iPage,queryWrapper);
    }

    @Override
    public PerformanceAppraisal findByDateAndAccount(String date, String account) {
        QueryWrapper<PerformanceAppraisal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(PerformanceAppraisal::getAccount, account)
            .eq(PerformanceAppraisal::getDate, date)
            .orderByDesc(PerformanceAppraisal::getCreateTime);

        List<PerformanceAppraisal> list = list(queryWrapper);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }

        return list.get(0);
    }

}
