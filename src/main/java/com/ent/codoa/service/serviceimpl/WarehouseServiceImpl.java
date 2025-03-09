package com.ent.codoa.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Warehouse;
import com.ent.codoa.mapper.WarehouseMapper;
import com.ent.codoa.pojo.req.warehouse.WarehouseBaseUpdate;
import com.ent.codoa.pojo.req.warehouse.WarehousePage;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.WarehouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {
    @Override
    public IPage<Warehouse> queryPage(WarehousePage dto) {
        IPage<Warehouse> ipage=new Page<>(dto.getPageNum(),dto.getPageSize());
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda() //根据系统用户所属账号来过滤，进行分页查询，只能查到创建人所属系统用户账号的数据
                .eq(Warehouse::getSystemClientAccount,TokenTools.getAdminAccount())
                .eq(StringUtils.isNotBlank(dto.getName()),Warehouse::getName,dto.getName())
                .orderByDesc(Warehouse::getCreateTime);

        ipage=page(ipage,queryWrapper);
        return ipage;
    }

    @Override
    public void add(Warehouse dto) {
        Warehouse wirehouse=findWarehouseByName(dto.getName());
        if(wirehouse!=null){
            throw new DataException("仓库名称重复");
        }
        LoginToken loginToken=TokenTools.getLoginToken(true);
        dto.setCreateName(TokenTools.getAdminName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAdminAccount());//系统用户所属账号通过登录后返回的token获取，存入对应表字段
        save(dto);
        LogTools.addLog("库存管理-新增仓库","新增一个仓库，信息："+ JSONUtil.toJsonStr(dto),loginToken);
    }

    @Override
    public void updateBaseInfo(WarehouseBaseUpdate dto) {
        UpdateWrapper<Warehouse> updateWrapper=new UpdateWrapper<>();
        LoginToken loginToken=TokenTools.getLoginToken(true);
        updateWrapper.lambda()
                .set(Warehouse::getName,dto.getName())
                .set(Warehouse::getAddress,dto.getAddress())
                .eq(Warehouse::getId,dto.getId());
        update(updateWrapper);
        LogTools.addLog("库存管理","编辑仓库，信息："+ JSONUtil.toJsonStr(dto),loginToken);
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public Warehouse findWarehouseByName(String name) {
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper();
        queryWrapper.lambda()
                .eq(Warehouse::getName,name);
        return getOne(queryWrapper);
    }

    @Override
    public List<Warehouse> findWarehouseList() {
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Warehouse::getSystemClientAccount,TokenTools.getAdminAccount())
                .orderByDesc(Warehouse::getCreateTime);
        List<Warehouse> list=list(queryWrapper);
        Set<Warehouse> set=new HashSet<>(list);    //去重
        List<Warehouse> distinctList=new ArrayList<>(set);
        return distinctList;
    }

    @Override
    public Warehouse findWarehouseById(long id) {
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Warehouse::getId,id);
        return getOne(queryWrapper);
    }


}
