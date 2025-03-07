package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.mapper.CustomerOrderMapper;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderInfoUpdate;
import com.ent.codoa.pojo.req.customerorder.CustomerOrderPage;
import com.ent.codoa.service.CustomerOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerOrderServiceImpl extends ServiceImpl<CustomerOrderMapper, CustomerOrder> implements CustomerOrderService {

    @Override
    public IPage<CustomerOrder> queryPage(CustomerOrderPage dto) {
        IPage<CustomerOrder> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<CustomerOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getName()), CustomerOrder::getName, dto.getName())
            .eq(StringUtils.isNotBlank(dto.getOrderNum()), CustomerOrder::getOrderNum, dto.getOrderNum())
            .eq(dto.getPayStatus() != null, CustomerOrder::getPayStatus, dto.getPayStatus())
            .eq(dto.getStatus() != null, CustomerOrder::getStatus, dto.getStatus())
            .eq(CustomerOrder::getSystemClientAccount, TokenTools.getAdminAccount())
            .orderByDesc(CustomerOrder::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void delete(Long id) {
        removeById(id);

        LogTools.addLog("客户管理", "删除客户订单记录id:" + id, TokenTools.getAdminToken(true));
    }


    @Override
    public void add(CustomerOrder dto) {
        dto.setCreateTime(LocalDateTime.now());
        dto.setCreateName(TokenTools.getAdminName());
        dto.setSystemClientAccount(TokenTools.getAdminAccount());
        save(dto);

        LogTools.addLog("客户管理", "添加客户订单", TokenTools.getAdminToken(true));
    }

    @Override
    public void updateInfo(CustomerOrderInfoUpdate dto) {
        updateById(BeanUtil.toBean(dto, CustomerOrder.class));

        LogTools.addLog("客户管理", "修改客户订单信息:" + JSONUtil.toJsonStr(dto), TokenTools.getAdminToken(true));
    }

}
