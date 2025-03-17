package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.constant.enums.CustomerWorkOrderStatusEnum;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.CustomerWorkOrder;
import com.ent.codoa.mapper.CustomerWorkOrderMapper;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderAdd;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderPage;
import com.ent.codoa.pojo.req.customerworkorder.CustomerWorkOrderStatusUpdate;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.CustomerWorkOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerWorkOrderServiceImpl extends ServiceImpl<CustomerWorkOrderMapper, CustomerWorkOrder> implements CustomerWorkOrderService {

    @Override
    public IPage<CustomerWorkOrder> queryPage(CustomerWorkOrderPage dto) {
        IPage<CustomerWorkOrder> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<CustomerWorkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(CustomerWorkOrder::getSystemClientAccount, TokenTools.getAccount())
            .eq(StringUtils.isNotBlank(dto.getAccount()), CustomerWorkOrder::getAccount, dto.getAccount())
            .like(StringUtils.isNotBlank(dto.getTitle()), CustomerWorkOrder::getTitle, dto.getTitle())
            .orderByDesc(CustomerWorkOrder::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void updateStatus(CustomerWorkOrderStatusUpdate dto) {
        UpdateWrapper<CustomerWorkOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(CustomerWorkOrder::getStatus, dto.getStatus())
            .eq(CustomerWorkOrder::getId, dto.getId());
        update(updateWrapper);

        LogTools.addLog("客户工单","修改客户工单状态:" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));
    }

    @Override
    public void add(CustomerWorkOrderAdd dto) {
        LoginToken loginToken = TokenTools.getLoginToken(true);
        CustomerWorkOrder customerWorkOrder = BeanUtil.toBean(dto, CustomerWorkOrder.class);
        customerWorkOrder.setAccount(loginToken.getAccount());
        customerWorkOrder.setSystemClientAccount(loginToken.getSystemClientAccount());
        customerWorkOrder.setCreateName(loginToken.getName());
        customerWorkOrder.setCreateTime(LocalDateTime.now());
        customerWorkOrder.setStatus(CustomerWorkOrderStatusEnum.PENDING);
        save(customerWorkOrder);
    }

    @Override
    public IPage<CustomerWorkOrder> clientPage(PageBase dto) {
        IPage<CustomerWorkOrder> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<CustomerWorkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(CustomerWorkOrder::getAccount, TokenTools.getAccount())
            .orderByDesc(CustomerWorkOrder::getCreateTime);
        return page(iPage, queryWrapper);
    }

}
