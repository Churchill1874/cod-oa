package com.ent.codoa.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.CustomerWorkOrderDialogue;
import com.ent.codoa.mapper.CustomerWorkOrderDialogueMapper;
import com.ent.codoa.pojo.req.IdPageBase;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.customerworkorderdialogue.CustomerWorkOrderDialoguePage;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.CustomerWorkOrderDialogueService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerWorkOrderDialogueServiceImpl extends ServiceImpl<CustomerWorkOrderDialogueMapper, CustomerWorkOrderDialogue> implements CustomerWorkOrderDialogueService {

    @Override
    public IPage<CustomerWorkOrderDialogue> queryPage(CustomerWorkOrderDialoguePage dto) {
        IPage<CustomerWorkOrderDialogue> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<CustomerWorkOrderDialogue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(CustomerWorkOrderDialogue::getWorkOrderId, dto.getWorkOrderId())
            .orderByDesc(CustomerWorkOrderDialogue::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void systemClientReply(CustomerWorkOrderDialogue dto) {
        LoginToken loginToken = TokenTools.getLoginToken(true);
        dto.setIsCustomer(false);
        dto.setCreateTime(LocalDateTime.now());
        dto.setCreateName(loginToken.getName());
        dto.setAccount(loginToken.getAccount());
        save(dto);
    }

    @Override
    public void customerReply(CustomerWorkOrderDialogue dto) {
        LoginToken loginToken = TokenTools.getLoginToken(true);
        dto.setIsCustomer(true);
        dto.setCreateTime(LocalDateTime.now());
        dto.setCreateName(loginToken.getName());
        dto.setAccount(loginToken.getAccount());
        save(dto);
    }

    @Override
    public IPage<CustomerWorkOrderDialogue> clientPage(CustomerWorkOrderDialoguePage dto) {
        IPage<CustomerWorkOrderDialogue> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<CustomerWorkOrderDialogue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(CustomerWorkOrderDialogue::getWorkOrderId, dto.getWorkOrderId())
            .orderByDesc(CustomerWorkOrderDialogue::getCreateTime);
        return page(iPage, queryWrapper);
    }

}
