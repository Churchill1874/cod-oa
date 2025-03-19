package com.ent.codoa.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.constant.enums.RoleEnum;
import com.ent.codoa.common.constant.enums.SystemClientStatusEnum;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.CodeTools;
import com.ent.codoa.common.tools.GenerateTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.SystemClient;
import com.ent.codoa.mapper.SystemClientMapper;
import com.ent.codoa.pojo.req.systemclient.SystemClientPage;
import com.ent.codoa.pojo.req.systemclient.SystemClientBaseUpdate;
import com.ent.codoa.service.SystemClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SystemClientServiceImpl extends ServiceImpl<SystemClientMapper, SystemClient> implements SystemClientService {

    @Override
    public IPage<SystemClient> queryPage(SystemClientPage dto) {
        // 存入构造器 要分页的页号参数 和 每页显示几条参数
        // 注意:因为IPage是mybatisplus 用来自动给你分页转化的对象 它要求必须通过表相对的实体类的声明来识别分页的表 <实体类>
        IPage<SystemClient> iPage = new Page<>(dto.getPageNum(), dto.getPageSize());

        //queryWrapper是mybatisPlus的查询过滤条件对象,包含了很多查询过滤快捷方法,但是同样
        //需要你指定 这个要查询过滤的表是哪个表 你点进去SystemClient来看就明白了 ,这个类名上面我用了一个注解声明了对应哪个 表名
        QueryWrapper<SystemClient> queryWrapper = new QueryWrapper<>();
        //.lambda方法查询方式 可以用 实体类名::的方式获取要查的字段 比如 SystemClient::getAccount
        //意思是说生成sql 的时候 这行代码用 account这个字段, 这个字段对应着表里面的哪个字段？你点进来这个实体类这个字段的注解决定的
        queryWrapper.lambda()
            //.eq方法有三个参数,第一个参数是条件,满足什么情况,才生成这个查询 account=po.getAccount的值？
            //po.getAccount这个参数不为空的时候 即 不为空字符串'' 也不为null的时候 这是 StringUtils.isNotBlank()这个方法校验的
            .eq(StringUtils.isNotBlank(dto.getAccount()), SystemClient::getAccount, dto.getAccount())
            .eq(StringUtils.isNotBlank(dto.getName()), SystemClient::getName, dto.getName())
            .eq(dto.getStatus() != null, SystemClient::getStatus, dto.getStatus())
            //降序排序 的方法 根据createTime字段的值排序降序
            .orderByDesc(SystemClient::getCreateTime);

        //将分页的号码参数 和 过滤查询条件的参数对象一起 放入mybaitsplus的 page()方法,就可以了
        //mybatisplus将自动 拼装分页sql 和返回对象结果
        iPage = page(iPage, queryWrapper);
        return iPage;
    }

    @Override
    public void add(SystemClient dto) {
        //添加之前要先校验 人事管理系统和客户管理系统是否有同样的account名称 没有才能添加 保证全局唯一账号 公用登录页面
        if (StringUtils.isBlank(dto.getCreateName())){
            dto.setCreateName(TokenTools.getName());
        }
        if (dto.getCreateTime() == null){
            dto.setCreateTime(LocalDateTime.now());
        }

        SystemClient systemClient = findByAccount(dto.getAccount());
        if (systemClient != null){
            throw new DataException("账号重复已存在");
        }

        dto.setRole(RoleEnum.ADMIN);
        dto.setStatus(SystemClientStatusEnum.NORMAL);
        dto.setSalt(GenerateTools.getUUID());
        dto.setPassword(CodeTools.md5AndSalt(dto.getPassword(), dto.getSalt()));
        dto.setCreateTime(LocalDateTime.now());
        dto.setCreateName(TokenTools.getName());
        save(dto);
    }

    @Override
    public void editBaseInfo(SystemClientBaseUpdate dto) {
        UpdateWrapper<SystemClient> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(SystemClient::getName, dto.getName())
            .set(SystemClient::getIntroduce, dto.getIntroduce())
            .set(SystemClient::getExpiredTime, dto.getExpiredTime())
            .eq(SystemClient::getId, dto.getId());
        update(updateWrapper);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public SystemClient findByAccount(String account) {
        QueryWrapper<SystemClient> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SystemClient::getAccount, account);
        return getOne(queryWrapper);
    }

    @Override
    public void updateStatus(Long id, SystemClientStatusEnum status) {
        UpdateWrapper<SystemClient> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
            .set(SystemClient::getStatus, status)
            .eq(SystemClient::getId, id);
        update(updateWrapper);
    }

}
