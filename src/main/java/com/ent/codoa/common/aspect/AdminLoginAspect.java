package com.ent.codoa.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AdminLoginAspect {

    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.ent.codoa.common.annotation.AdminLoginCheck)")
    public void adminLoginCheck() {

    }

    @Before("adminLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
/*
        TokenTools.getLoginToken(true);
*/
    }

/*    @After("loginCheck()")
    public void afterCut(){

    }*/


}
