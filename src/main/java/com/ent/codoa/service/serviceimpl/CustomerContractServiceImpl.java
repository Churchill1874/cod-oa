package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.CustomerContract;
import com.ent.codoa.mapper.CustomerContractMapper;
import com.ent.codoa.pojo.req.customercontract.CustomerContractAdd;
import com.ent.codoa.pojo.req.customercontract.CustomerContractPage;
import com.ent.codoa.pojo.req.customercontract.CustomerContractUpdate;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.CustomerContractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerContractServiceImpl extends ServiceImpl<CustomerContractMapper, CustomerContract> implements CustomerContractService {

    @Override
    public IPage<CustomerContract> queryPage(CustomerContractPage dto) {
        IPage<CustomerContract> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<CustomerContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getName()), CustomerContract::getName, dto.getName())
            .eq(StringUtils.isNotBlank(dto.getAccount()), CustomerContract::getAccount, dto.getAccount())
            .eq(CustomerContract::getSystemClientAccount, TokenTools.getAccount())
            .orderByDesc(CustomerContract::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void add(CustomerContractAdd dto) {
        LoginToken loginToken = TokenTools.getLoginToken(true);
        CustomerContract customerContract = BeanUtil.toBean(dto, CustomerContract.class);
        customerContract.setCreateTime(LocalDateTime.now());
        customerContract.setCreateName(loginToken.getName());
        customerContract.setSystemClientAccount(loginToken.getAccount());
        save(customerContract);

        LogTools.addLog("合同管理", "添加客户合同配置记录:" + JSONUtil.toJsonStr(dto), loginToken);
    }

    @Override
    public void updateBase(CustomerContractUpdate dto) {
        LoginToken loginToken = TokenTools.getLoginToken(true);
        UpdateWrapper<CustomerContract> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(CustomerContract::getContract, dto.getContract())
            .set(CustomerContract::getName, dto.getName())
            .set(CustomerContract::getAccount, dto.getAccount())
            .set(CustomerContract::getRemark, dto.getRemark())
            .eq(CustomerContract::getId, dto.getId());
        update(updateWrapper);

        LogTools.addLog("合同管理", "修改客户合同配置记录:" + JSONUtil.toJsonStr(dto), loginToken);
    }

    @Override
    public void delete(Long id) {
        LoginToken loginToken = TokenTools.getLoginToken(true);

        removeById(id);

        LogTools.addLog("合同管理", "删除客户合同记录:" + id, loginToken);
    }

}
