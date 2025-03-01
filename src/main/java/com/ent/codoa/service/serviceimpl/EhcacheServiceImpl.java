package com.ent.codoa.service.serviceimpl;

import com.ent.codoa.pojo.resp.token.AdminTokenResp;
import com.ent.codoa.service.EhcacheService;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.GenerateTools;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 该类对ehcache.xml配置文件里面已经配置的缓存容器进行实现获取，方便使用
 */
@Slf4j
@Service
public class EhcacheServiceImpl implements EhcacheService {
    //验证码
    final String VERIFICATION_CODE = "captcha_code";
    //管理员登录token
    final String ADMIN_TOKEN = "admin_token";
    
    @Autowired
    private CacheManager cacheManager;


    @Override
    public Cache<String, String> captchaCodeCache() {
        return cacheManager.getCache(VERIFICATION_CODE, String.class, String.class);
    }


    @Override
    public String getVC(String key, Integer limitCount, String remarks) {
        //添加频繁点击校验 3秒内点击超过30次 检查警告日志 如果该ip已经存在警告则拉黑 不存在则新加警告日志
        //todo 待更新新校验黑名单
        //this.checkIp3SecondsClick(limitCount, remarks);

        //获取验证码
        String codeImageStream = null;
        String code = null;
        try {
            code = GenerateTools.getCaptchaText(5);
            codeImageStream = GenerateTools.getCaptchaImage(code);
        } catch (IOException e) {
            log.error("生成验证码异常:{}", e.getMessage());
            throw new DataException(e.getMessage());
        }

        captchaCodeCache().put(key, code);
        return codeImageStream;
    }


    @Override
    public Cache<String, AdminTokenResp> adminTokenCache() {
        return cacheManager.getCache(ADMIN_TOKEN, String.class, AdminTokenResp.class);
    }


}
