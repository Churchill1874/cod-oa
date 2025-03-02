package com.ent.codoa.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.common.tools.CodeTools;
import com.ent.codoa.common.tools.GenerateTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.BusinessClient;
import com.ent.codoa.mapper.BusinessClientMapper;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.businessclient.BusinessClientBaseUpdate;
import com.ent.codoa.service.BusinessClientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BusinessClientServiceImpl extends ServiceImpl<BusinessClientMapper, BusinessClient> implements BusinessClientService {


    @Override
    public IPage<BusinessClient> queryPage(PageBase pageBase) {
         IPage<BusinessClient> iPage = new Page<>(pageBase.getPageNum(), pageBase.getPageSize());
        QueryWrapper<BusinessClient> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(BusinessClient::getSystemClientAccount, TokenTools.getAdminAccount())
            .orderByDesc(BusinessClient::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public BusinessClient findByAccount(String account) {
        QueryWrapper<BusinessClient> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(BusinessClient::getAccount, account);
        return getOne(queryWrapper);
    }

    @Override
    public void add(BusinessClient dto) {
        dto.setCreateName("系统用户");
        dto.setCreateTime(LocalDateTime.now());
        dto.setStatus(UserStatusEnum.NORMAL);
        dto.setSystemClientAccount(TokenTools.getAdminAccount());
        dto.setSalt(GenerateTools.getUUID());
        dto.setPassword(CodeTools.md5AndSalt(dto.getPassword(), dto.getSalt()));
        save(dto);
    }

    @Override
    public void updateBaseInfo(BusinessClientBaseUpdate dto) {
        UpdateWrapper<BusinessClient> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(BusinessClient::getName, dto.getName())
            .eq(BusinessClient::getId, dto.getId());
        update(updateWrapper);
    }

    @Override
    public void updateStatus(Long id, UserStatusEnum status) {
        UpdateWrapper<BusinessClient> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(BusinessClient::getStatus, status)
            .eq(BusinessClient::getId, id);
        update(updateWrapper);
    }

}
