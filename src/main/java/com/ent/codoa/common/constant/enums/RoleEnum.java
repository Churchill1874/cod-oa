package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 角色枚举
 * @author admin
 */
public enum RoleEnum {
    SUPER_ADMIN(1, "超级管理员"),
    ADMIN(2, "系统管理员"),
    CUSTOMER(3,"客户管理的客户"),
    HUMAN_RESOURCE_MANAGEMENT(4,"人事管理的职员");

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    RoleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }

}
