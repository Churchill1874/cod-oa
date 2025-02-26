package com.ent.codoa.common.aspect;

import com.ent.codoa.common.constant.enums.ManageRoleEnum;
import com.ent.codoa.common.exception.AuthException;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.pojo.resp.admin.AdminTokenResp;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SuperAdminCheck {

    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.ent.codoa.common.annotation.SuperAdminLoginCheck)")
    public void superAdminLoginCheck() {

    }

    @Before("superAdminLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
        AdminTokenResp adminTokenResp = TokenTools.getAdminToken(true);
        if (adminTokenResp.getRole() != ManageRoleEnum.SUPER_ADMIN) {
            throw new AuthException();
        }
    }


}
