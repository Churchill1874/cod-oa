package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 系统用户状态枚举
 */
public enum SystemClientStatusEnum {

    EXPIRED("到期",0),
    NORMAL("正常",1),
    DISABLE("禁用",2);

    @Getter
    @EnumValue
    @JsonValue
    private Integer code;

    @Getter
    private String name;

    SystemClientStatusEnum(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }

}
