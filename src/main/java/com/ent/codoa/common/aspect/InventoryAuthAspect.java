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
public class InventoryAuthAspect {

    @Pointcut("@annotation(com.ent.codoa.common.annotation.InventoryAuthCheck)")
    public void inventoryAuthPointCut(){

    }

    @Before("inventoryAuthPointCut()")
    public void beforeCut(JoinPoint joinPoint){
        LoginToken loginToken = TokenTools.getLoginToken(true);
        if (!loginToken.getInventoryMenu()){
            throw new DataException("库存管理操作权限受限,请联系管理员");
        }
    }



}
