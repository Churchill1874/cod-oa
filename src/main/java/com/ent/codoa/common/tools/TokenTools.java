package com.ent.codoa.common.tools;

import com.ent.codoa.common.exception.TokenException;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.EhcacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * token工具类
 */
@Component
public class TokenTools {

    private static EhcacheService ehcacheService;

    //因为static修饰成员变量不支持自动注入 所以以下面方式给静态成员注入
    @Autowired
    public void setEhcacheService(EhcacheService ehcacheService) {
        TokenTools.ehcacheService = ehcacheService;
    }


    /**
     * 获取管理员登录信息
     * @return
     */
    public static LoginToken getLoginToken(boolean needCheck) {
        String headerToken = HttpTools.getHeaderToken();
        if (StringUtils.isBlank(headerToken)){
            //如果要求在请求头里的token_id不能为空 要校验令牌
            if (needCheck){
                throw new TokenException();
            } else {
                return null;
            }
        }

        LoginToken loginToken = ehcacheService.adminTokenCache().get(headerToken);
        if (loginToken == null) {
            throw new TokenException();
        }
        return loginToken;
    }


    /**
     * 获取管理员名称
     * @return
     */
    public static String getAdminName () {
        return getLoginToken(true).getName();
    }

    /**
     * 获取管理员账号
     * @return
     */
    public static String getAdminAccount() {
        return getLoginToken(true).getAccount();
    }


}
