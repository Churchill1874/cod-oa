package com.ent.codoa.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.OperationLog;
import com.ent.codoa.mapper.OperationLogMapper;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.OperationLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public IPage<OperationLog> queryPage(PageBase dto) {
        IPage<OperationLog> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .ge(dto.getStartTime() != null, OperationLog::getCreateTime, dto.getStartTime())
            .le(dto.getEndTime() != null, OperationLog::getCreateTime, dto.getEndTime())
            .orderByDesc(OperationLog::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Async
    @Override
    public void add(OperationLog dto) {
        dto.setCreateTime(LocalDateTime.now());
        save(dto);
    }

}
