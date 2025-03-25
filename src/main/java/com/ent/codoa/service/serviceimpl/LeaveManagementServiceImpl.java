package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.LeaveManagement;
import com.ent.codoa.mapper.LeaveManagementMapper;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.leavemanagement.LeaveManagementAdd;
import com.ent.codoa.pojo.req.leavemanagement.LeaveManagementPage;
import com.ent.codoa.service.LeaveManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class LeaveManagementServiceImpl extends ServiceImpl<LeaveManagementMapper, LeaveManagement> implements LeaveManagementService {

    @Override
    public IPage<LeaveManagement> queryPage(LeaveManagementPage dto) {
        IPage<LeaveManagement> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<LeaveManagement> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getAccount()), LeaveManagement::getAccount, dto.getAccount())
            .eq(LeaveManagement::getSystemClientAccount, TokenTools.getAccount())
            .orderByDesc(LeaveManagement::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public IPage<LeaveManagement> clientPage(PageBase dto) {
        IPage<LeaveManagement> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<LeaveManagement> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(LeaveManagement::getAccount, TokenTools.getAccount())
            .orderByDesc(LeaveManagement::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void add(LeaveManagementAdd dto) {
        LeaveManagement leaveManagement = BeanUtil.toBean(dto, LeaveManagement.class);
        leaveManagement.setLeaveDaysCount(ChronoUnit.DAYS.between(leaveManagement.getStartDate(), leaveManagement.getEndDate()));
        leaveManagement.setCreateTime(LocalDateTime.now());
        leaveManagement.setCreateName(TokenTools.getName());
        leaveManagement.setSystemClientAccount(TokenTools.getSystemClientAccount());
        leaveManagement.setAccount(TokenTools.getAccount());
        leaveManagement.setStatus("待审批");
        save(leaveManagement);

    }

    @Override
    public void delete(Long id) {
        removeById(id);
        LogTools.addLog("休假管理", "删除一条休假记录:" + id, TokenTools.getLoginToken(true));
    }

    @Override
    public void approval(Long id, String status) {
        UpdateWrapper<LeaveManagement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(LeaveManagement::getStatus, status)
            .set(LeaveManagement::getApprovalAccount, TokenTools.getAccount())
            .set(LeaveManagement::getApprovalTime, LocalDateTime.now())
            .eq(LeaveManagement::getId, id);

        update(updateWrapper);

        LogTools.addLog("休假管理", "审批休假:" + id + ",状态改为:" + status, TokenTools.getLoginToken(true));
    }

}
