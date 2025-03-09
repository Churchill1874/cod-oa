package com.ent.codoa.common.tools;

import com.ent.codoa.entity.OperationLog;
import com.ent.codoa.pojo.resp.token.LoginToken;
import com.ent.codoa.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志工具
 */

@Component
public class LogTools {

    private static OperationLogService operationLogService;

    //因为static修饰成员变量不支持自动注入 所以以下面方式给静态成员注入
    @Autowired
    public void setOperationLogService(OperationLogService operationLogService) {
        LogTools.operationLogService = operationLogService;
    }

    public static void addLog(String menu, String introduce, LoginToken loginToken){
        OperationLog operationLog = new OperationLog();
        operationLog.setAccount(loginToken.getAccount());
        operationLog.setMenu(menu);
        operationLog.setIntroduce(introduce);
        operationLog.setCreateName(loginToken.getName());
        operationLogService.add(operationLog);
    }

}
