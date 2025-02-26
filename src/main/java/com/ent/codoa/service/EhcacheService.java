package com.ent.codoa.service;


import org.ehcache.Cache;

/**
 * 缓存服务
 */
public interface EhcacheService {


    /**
     * 获取验证码缓存容器
     * @return
     */
    Cache<String, String> verificationCache();



    /**
     * 获取验证码 并设置每3秒的限制请求次数 和提示语
     * @param limitCount
     * @param remarks
     * @return
     */
    String getVC(String key, Integer limitCount, String remarks);


}
