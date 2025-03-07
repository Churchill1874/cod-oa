package com.ent.codoa.service.serviceimpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.constant.enums.OperationTypeEnum;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.StockOperation;
import com.ent.codoa.mapper.StockOperationMapper;
import com.ent.codoa.service.StockOperationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StockOperationServiceImpl extends ServiceImpl<StockOperationMapper, StockOperation> implements StockOperationService {
    @Override
    public void stockInAdd(StockOperation dto) {

        dto.setOperationType(OperationTypeEnum.STOCKIN);
        dto.setCreateName(TokenTools.getAdminName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAdminAccount());
        save(dto);

    }

    @Override
    public void stockOutAdd(StockOperation dto) {
        dto.setOperationType(OperationTypeEnum.STOCKOUT);
        dto.setCreateName(TokenTools.getAdminName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAdminAccount());
        save(dto);
    }

    @Override
    public void stockReturnAdd(StockOperation dto) {
        dto.setOperationType(OperationTypeEnum.TOBERETURN);
        dto.setCreateName(TokenTools.getAdminName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAdminAccount());
        save(dto);
    }
}
