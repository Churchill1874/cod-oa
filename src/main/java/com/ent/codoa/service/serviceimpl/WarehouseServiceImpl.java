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
import java.util.*;

@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {
    @Override
    public IPage<Warehouse> queryPage(WarehousePage dto) {
        IPage<Warehouse> ipage=new Page<>(dto.getPageNum(),dto.getPageSize());
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda() //根据系统用户所属账号来过滤，进行分页查询，只能查到创建人所属系统用户账号的数据
                .eq(Warehouse::getSystemClientAccount,TokenTools.getAccount())
                .eq(StringUtils.isNotBlank(dto.getName()),Warehouse::getName,dto.getName())
                .orderByDesc(Warehouse::getCreateTime);

        ipage=page(ipage,queryWrapper);
        return ipage;
    }

    @Override
    public void add(Warehouse dto) {
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper();
        queryWrapper.lambda()
                .eq(Warehouse::getName,dto.getName())
                .eq(Warehouse::getSystemClientAccount,TokenTools.getAccount());
        Warehouse wirehouse=getOne(queryWrapper);
        if(wirehouse!=null){
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("仓库名称重复");
            }else{
                throw new DataException("倉庫名が重複しています。");
            }
        }
        LoginToken loginToken=TokenTools.getLoginToken(true);
        dto.setCreateName(TokenTools.getName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setSystemClientAccount(TokenTools.getAccount());//系统用户所属账号通过登录后返回的token获取，存入对应表字段
        save(dto);
        LogTools.addLog("库存管理-新增仓库","新增一个仓库，信息："+ JSONUtil.toJsonStr(dto),loginToken);
    }

    @Override
    public void updateBaseInfo(WarehouseBaseUpdate dto) {
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper();
        queryWrapper.lambda()
                .eq(Warehouse::getName,dto.getName())
                .eq(Warehouse::getSystemClientAccount,TokenTools.getAccount());
        Warehouse wirehouse=getOne(queryWrapper);
        if(wirehouse!=null){
            if("cn".equals(TokenTools.getLoginLang())){
                throw new DataException("仓库名称重复");
            }else{
                throw new DataException("倉庫名が重複しています。");
            }
        }
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
        removeById(id);
        LogTools.addLog("库存管理","删除仓库，Id："+ id,TokenTools.getLoginToken(true));
    }



    @Override
    public List<Map> findWarehouseList() {
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Warehouse::getSystemClientAccount,TokenTools.getAccount())
                .orderByDesc(Warehouse::getCreateTime);
        List<Warehouse> list=list(queryWrapper);
        List<Map> newList = new ArrayList<>();
        for(Warehouse warehouse:list){
            Map map=new HashMap();
            map.put("warehouseId",warehouse.getId());
            map.put("name",warehouse.getName());
            newList.add(map);
        }
        return newList;
    }

    @Override
    public Warehouse findWarehouseById(Long id) {
        QueryWrapper<Warehouse> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Warehouse::getId,id);
        return getOne(queryWrapper);
    }


}
