package com.ent.codoa.config;
import com.ent.codoa.common.constant.enums.RoleEnum;

import com.ent.codoa.common.constant.enums.SystemClientStatusEnum;
import com.ent.codoa.common.tools.CodeTools;
import com.ent.codoa.common.tools.GenerateTools;
import com.ent.codoa.entity.SystemClient;
import com.ent.codoa.service.SystemClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Component
public class InitConfig {

    @Autowired
    private SystemClientService systemClientService;



    //超管管理员账号
    private static final String SUPER_ADMIN_ACCOUNT = "admin";

    private static final String PASSWORD = "111111a";

    //获取创建机器人开关
    @Value("${init.create.bot}")
    private boolean createBot;


    /**
     * 项目启动时运行方法
     */
    @PostConstruct
    private void run() {
        SystemClient systemClient = systemClientService.findByAccount(SUPER_ADMIN_ACCOUNT);

        if (systemClient == null){
            systemClient = new SystemClient();
            systemClient.setAccount(SUPER_ADMIN_ACCOUNT);
            systemClient.setName("超级管理员");
            systemClient.setStatus(SystemClientStatusEnum.NORMAL);
            systemClient.setIntroduce("超级管理员的开发账号");
            systemClient.setLastLoginTime(LocalDateTime.now());
            systemClient.setRole(RoleEnum.SUPER_ADMIN);
            String salt = GenerateTools.getUUID();
            systemClient.setSalt(salt);
            systemClient.setCreateTime(LocalDateTime.now());
            systemClient.setCreateName("系统初始化");
            systemClient.setPassword(CodeTools.md5AndSalt(PASSWORD, salt));
            systemClientService.add(systemClient);
            log.info("初始化了超级管理员");
        }
    }


}
