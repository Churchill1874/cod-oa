package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import com.ent.codoa.entity.Staff;
import com.ent.codoa.mapper.StaffMapper;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.staff.StaffBaseUpdate;
import com.ent.codoa.pojo.req.staff.StaffPage;
import com.ent.codoa.pojo.req.staff.StaffStatusUpdate;
import com.ent.codoa.service.CustomerService;
import com.ent.codoa.service.StaffService;
import com.ent.codoa.service.SystemClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {

    @Autowired
    private SystemClientService systemClientService;

    @Autowired
    private CustomerService customerService;

    @Override
    public IPage<Staff> queryPage(StaffPage dto) {
        IPage<Staff> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(StringUtils.isNotBlank(dto.getAccount()), Staff::getAccount, dto.getAccount())
            .eq(StringUtils.isNotBlank(dto.getName()), Staff::getName, dto.getName())
            .eq(StringUtils.isNotBlank(dto.getPhone()), Staff::getPhone, dto.getPhone())
            .eq(Staff::getSystemClientAccount, TokenTools.getAccount())
            .orderByDesc(Staff::getCreateTime);
        return page(iPage, queryWrapper);
    }

    @Override
    public void updateStatus(StaffStatusUpdate dto) {
        UpdateWrapper<Staff> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(Staff::getStatus, dto.getStatus())
            .eq(Staff::getId, dto.getId());
        update(updateWrapper);

        LogTools.addLog("人事管理", "修改了一名员工状态,信息:" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));
    }

    @Override
    public void updateBase(StaffBaseUpdate dto) {
        LambdaUpdateWrapper<Staff> updateWrapper = new LambdaUpdateWrapper<>();
        // 设置需要更新的字段
        updateWrapper
            .set(Staff::getName, dto.getName())
            .set(Staff::getAge, dto.getAge())
            .set(Staff::getBirth, dto.getBirth())
            .set(Staff::getGender, dto.getGender())
            .set(Staff::getPhone, dto.getPhone())
            .set(Staff::getEmail, dto.getEmail())
            .set(Staff::getAddress, dto.getAddress())
            .set(Staff::getDepartment, dto.getDepartment())
            .set(Staff::getPosition, dto.getPosition())
            .set(Staff::getLevel, dto.getLevel())
            .set(Staff::getSalary, dto.getSalary())
            .set(Staff::getHireDate, dto.getHireDate())
            .set(Staff::getWorkStatus, dto.getWorkStatus())
            .set(Staff::getOfferStatus, dto.getOfferStatus())
            .set(Staff::getStatus, dto.getStatus())
            .set(Staff::getWeekdayOvertimePayRate, dto.getWeekdayOvertimePayRate())
            .set(Staff::getWeekendOvertimePayRate, dto.getWeekendOvertimePayRate())
            .eq(Staff::getId, dto.getId()); // 根据 ID 更新
        update(updateWrapper);

        LogTools.addLog("人事管理", "修改了一名员工信息,信息:" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));
    }

    @Override
    public void add(Staff dto) {
        if (findByAccount(dto.getAccount()) != null
            || systemClientService.findByAccount(dto.getAccount()) != null
            || customerService.findByAccount(dto.getAccount()) != null) {
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("账号已经存在,请修改");
            }else{
                throw new DataException("アカウントは既に存在しています。変更してください");
            }
        }

        if (StringUtils.isNotBlank(dto.getCode())){
            QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Staff::getCode, dto.getCode());
            if (getOne(queryWrapper) != null){
                if("cn".equals(TokenTools.getLoginLang())){
                    throw new DataException("员工编码重复");
                }else{
                    throw new DataException("社員コードが重複しています");
                }
            }
        }

        dto.setSalt(GenerateTools.getUUID());
        dto.setPassword(CodeTools.md5AndSalt(dto.getPassword(), dto.getSalt()));
        dto.setSystemClientAccount(TokenTools.getAccount());
        dto.setCreateName(TokenTools.getName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setStatus(UserStatusEnum.NORMAL);
        save(dto);

        LogTools.addLog("人事管理", "添加了一名员工,信息:" + JSONUtil.toJsonStr(dto), TokenTools.getLoginToken(true));
    }

    @Override
    public Staff findByAccount(String account) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Staff::getAccount, account);
        return getOne(queryWrapper);
    }

    @Override
    public void updateContract(Long id, String contract) {
        UpdateWrapper<Staff> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(Staff::getContract, contract)
            .eq(Staff::getId, id);
        update(updateWrapper);

        LogTools.addLog("人事管理", "更新员工合同,id:" + id, TokenTools.getLoginToken(true));
    }

    @Override
    public IPage<Staff> aboutToExpirePage(PageBase dto) {
        IPage<Staff> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Staff> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .le(Staff::getEmploymentExpire, LocalDate.now().plusMonths(3))
            .eq(Staff::getWorkStatus, "在职")
            .eq(Staff::getSystemClientAccount, TokenTools.getAccount())
            .orderByDesc(Staff::getCreateTime);
        return page(iPage, queryWrapper);
    }


}
