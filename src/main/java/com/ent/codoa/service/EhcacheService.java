package com.ent.codoa.service;


import com.ent.codoa.pojo.resp.token.LoginToken;
import org.ehcache.Cache;

import java.util.Set;

/**
 * 缓存服务
 */
public interface EhcacheService {


    /**
     * 获取验证码缓存容器
     * @return
     */
    Cache<String, Set<String>> captchaCodeCache();

    /**
     * 获取验证码 并设置每3秒的限制请求次数 和提示语
     * @param limitCount
     * @param remarks
     * @return
     */
    String getVC( Integer limitCount, String remarks);

    /**
     * 管理员登录token
     * @return
     */
    Cache<String, LoginToken> adminTokenCache();



}
