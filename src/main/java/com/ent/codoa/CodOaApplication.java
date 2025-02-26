package com.ent.codoa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync //开启异步注解功能
@EnableCaching //开启缓存
@EnableScheduling //开启定时任务
@SpringBootApplication
@MapperScan("com.ent.codoa.mapper")
public class CodOaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodOaApplication.class, args);
    }

}
