package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.PotentialClient;
import com.ent.codoa.mapper.PotentialClientMapper;
import com.ent.codoa.pojo.req.potentialclent.PotentialClientPage;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.PotentialClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PotentialClientServiceImpl extends ServiceImpl<PotentialClientMapper, PotentialClient> implements PotentialClientService {

    @Override
    public IPage<PotentialClient> queryPage(PotentialClientPage dto) {
        IPage<PotentialClient> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<PotentialClient> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getIntroduce()), PotentialClient::getIntroduce, dto.getIntroduce())
            .eq(StringUtils.isNotBlank(dto.getName()), PotentialClient::getName, dto.getName())
            .eq(StringUtils.isNotBlank(dto.getRemark()), PotentialClient::getRemark, dto.getRemark())
            .eq(PotentialClient::getSystemClientAccount , TokenTools.getAdminAccount())
            .orderByDesc(PotentialClient::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void add(PotentialClient dto) {
        LoginToken adminLogin = TokenTools.getLoginToken(true);
        dto.setCreateTime(LocalDateTime.now());
        dto.setCreateName(adminLogin.getName());
        dto.setSystemClientAccount(adminLogin.getAccount());
        save(dto);

        LogTools.addLog("客户管理", "添加潜在客户:" + JSONUtil.toJsonStr(dto), adminLogin);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
        LogTools.addLog("客户管理", "删除潜在客户:" + id, TokenTools.getLoginToken(true));
    }

    @Override
    public void updateInfo(PotentialClient dto) {
        UpdateWrapper<PotentialClient> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(PotentialClient::getName, dto.getName())
            .set(PotentialClient::getIntroduce, dto.getIntroduce())
            .set(PotentialClient::getContract, dto.getContract())
            .set(PotentialClient::getRemark, dto.getRemark())
            .set(PotentialClient::getStatus, dto.getStatus())
            .eq(PotentialClient::getId, dto.getId());
        update(updateWrapper);

        LogTools.addLog("客户管理", "修改潜在客户信息:" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));
    }

}
