package com.ent.codoa.common.tools;

import com.ent.codoa.common.constant.enums.SystemClientStatusEnum;
import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.exception.TokenException;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.EhcacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * token工具类
 */
@Slf4j
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
            //如果要求在请求头里的token-id不能为空 要校验令牌
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

        if (loginToken.getStatus() == SystemClientStatusEnum.EXPIRED){
            throw new DataException("服务已经到期,请联系平台管理员");
        }
        if (loginToken.getStatus() == SystemClientStatusEnum.DISABLE){
            throw new DataException("账号已禁用,请联系平台管理员");
        }
        return loginToken;
    }


    /**
     * 获取登录名称
     * @return
     */
    public static String getName () {
        return getLoginToken(true).getName();
    }

    /**
     * 获取登录账号
     * @return
     */
    public static String getAccount() {
        return getLoginToken(true).getAccount();
    }

    /**
     * 获取所属系统管理员账号
     * @return
     */
    public static String getSystemClientAccount() {
        return getLoginToken(true).getSystemClientAccount();
    }

    /**
     * 获取登录时选择的语言
     * @return
     */
    public static String getLoginLang(){
        return getLoginToken(true).getLang();
    }
}
