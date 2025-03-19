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
    public void stockIn(StockOperation dto) {

        dto.setOperationType(OperationTypeEnum.STOCKIN);
        dto.setCreateName(TokenTools.getName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAccount());
        save(dto);

    }

    @Override
    public void stockOut(StockOperation dto) {
        dto.setOperationType(OperationTypeEnum.STOCKOUT);
        dto.setCreateName(TokenTools.getName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAccount());
        save(dto);
    }

    @Override
    public void stockReturn(StockOperation dto) {
        dto.setOperationType(OperationTypeEnum.TOBERETURN);
        dto.setCreateName(TokenTools.getName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAccount());
        save(dto);
    }
}
