package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.constant.enums.ComplaintStatusEnum;
import com.ent.codoa.common.constant.enums.OrderStatusEnum;
import com.ent.codoa.common.constant.enums.PaymentStatusEnum;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Complaint;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.mapper.ComplaintMapper;
import com.ent.codoa.pojo.req.complaint.ComplaintPage;
import com.ent.codoa.pojo.req.complaint.ComplaintStatusUpdate;
import com.ent.codoa.service.ComplaintService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {


    @Override
    public IPage<Complaint> queryPage(ComplaintPage dto) {
        IPage<Complaint> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getAccount()), Complaint::getAccount, dto.getAccount())
            .eq(Complaint::getSystemClientAccount, TokenTools.getAdminAccount())
            .orderByDesc(Complaint::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void add(Complaint dto) {
        dto.setStatus(ComplaintStatusEnum.ALREADY_COMPLAINED);
        dto.setCreateName("");//todo 尤其修改成用户token中的名字
        dto.setCreateTime(LocalDateTime.now());
        save(dto);
    }

    @Override
    public void updateStatus(ComplaintStatusUpdate dto) {
        UpdateWrapper<Complaint> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(Complaint::getStatus, dto.getStatus())
            .eq(Complaint::getId, dto.getId());
        update(updateWrapper);

        LogTools.addLog("投诉记录","修改了状态:" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));
    }


    @Override
    public List<Complaint> withinThreeMonth() {
        //当前时间
        LocalDate thisMonth = LocalDate.now();
        //3个月之前的时间 并且设置从1日开始
        LocalDate last3Month = thisMonth.minusMonths(2).withDayOfMonth(1);

        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .ge(Complaint::getCreateTime, last3Month)
            .le(Complaint::getCreateTime, thisMonth)
            .eq(Complaint::getSystemClientAccount, TokenTools.getAdminAccount());
        return list(queryWrapper);
    }

}
