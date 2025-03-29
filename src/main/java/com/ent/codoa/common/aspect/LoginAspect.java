package com.ent.codoa.common.aspect;

import com.ent.codoa.common.tools.TokenTools;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    @Pointcut("@annotation(com.ent.codoa.common.annotation.LoginCheck)")
    public void loginPointCut(){

    }

    @Before("loginPointCut()")
    public void beforeCut(JoinPoint joinPoint){
        TokenTools.getLoginToken(true);
    }

}
