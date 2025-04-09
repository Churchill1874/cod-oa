package com.ent.codoa.common.aspect;

import com.ent.codoa.common.exception.DataException;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.pojo.resp.token.LoginToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StaffAuthAspect {

    @Pointcut("@annotation(com.ent.codoa.common.annotation.StaffAuthCheck)")
    public void staffAuthPointCut(){

    }

    @Before("staffAuthPointCut()")
    public void beforeCut(JoinPoint joinPoint){
        LoginToken loginToken = TokenTools.getLoginToken(true);
        if (!loginToken.getHrMenu()){
            if("cn".equals(loginToken.getLang())){
                throw new DataException("人事管理操作权限受限,请联系管理员");
            }
            if("jp".equals(loginToken.getLang())){
                throw new DataException("人事管理操作の権限が制限されています。システム管理者にご連絡ください。");
            }
            throw new DataException("参数异常");
        }
    }


}
