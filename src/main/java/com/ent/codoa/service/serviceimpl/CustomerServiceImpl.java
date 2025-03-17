package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.CodeTools;
import com.ent.codoa.common.tools.GenerateTools;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Customer;
import com.ent.codoa.mapper.CustomerMapper;
import com.ent.codoa.pojo.req.customer.CustomerBaseUpdate;
import com.ent.codoa.pojo.req.customer.CustomerPage;
import com.ent.codoa.pojo.req.customer.CustomerStatusUpdate;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.CustomerService;
import com.ent.codoa.service.OperationLogService;
import com.ent.codoa.service.StaffService;
import com.ent.codoa.service.SystemClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Lazy
    @Autowired
    private SystemClientService systemClientService;
    @Lazy
    @Autowired
    private StaffService staffService;


    @Override
    public IPage<Customer> queryPage(CustomerPage dto) {
        IPage<Customer> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getAccount()), Customer::getAccount, dto.getAccount())
            .eq(StringUtils.isNotBlank(dto.getName()), Customer::getName, dto.getName())
            .eq(StringUtils.isNotBlank(dto.getEnterpriseName()), Customer::getEnterpriseName, dto.getEnterpriseName())
            .eq(Customer::getSystemClientAccount, TokenTools.getAccount())
            .orderByDesc(Customer::getCreateTime);
        return page(iPage, queryWrapper);
    }


    @Override
    public void add(Customer dto) {
        if (findByAccount(dto.getAccount()) != null
            || systemClientService.findByAccount(dto.getAccount()) != null
            || staffService.findByAccount(dto.getAccount()) != null) {
            throw new DataException("账号已经存在,请修改");
        }

        LoginToken loginToken = TokenTools.getLoginToken(true);

        dto.setSystemClientAccount(loginToken.getAccount());
        dto.setCreateName(loginToken.getName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSalt(GenerateTools.getUUID());
        dto.setPassword(CodeTools.md5AndSalt(dto.getPassword(), dto.getSalt()));
        dto.setStatus(UserStatusEnum.NORMAL);
        save(dto);

        LogTools.addLog("客户管理","添加了一名客户,信息:" + JSONUtil.toJsonStr(dto), loginToken);
    }

    @Override
    public void updateBase(CustomerBaseUpdate dto) {
        UpdateWrapper<Customer> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda()
            .set(Customer::getName, dto.getName())
            .set(Customer::getEnterpriseName, dto.getEnterpriseName())
            .set(Customer::getAddress, dto.getAddress())
            .set(Customer::getContact, dto.getContact())
            .set(Customer::getIndustry, dto.getIndustry())
            .set(Customer::getScale, dto.getScale())
            .set(Customer::getIntroduce, dto.getIntroduce())
            .eq(Customer::getId,dto.getId());
        update(updateWrapper);

        LogTools.addLog("客户管理","修改了一名客户,信息:" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));
    }

    @Override
    public void updateStatus(CustomerStatusUpdate dto) {
        UpdateWrapper<Customer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(Customer::getStatus, dto.getStatus())
            .eq(Customer::getId, dto.getId());
        update(updateWrapper);

        LogTools.addLog("客户管理","修改了客户状态,信息:" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));
    }

    @Override
    public Customer findByAccount(String account) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Customer::getAccount, account);
        return getOne(queryWrapper);
    }

}
