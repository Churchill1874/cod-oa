package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 菜单枚举
 * @author admin
 */
public enum SystemMenuEnum {
    CUSTOMER(1, "客户管理"),
    INVENTORY(2, "库存管理"),
    HUMAN_RESOURCE_MANAGEMENT(3,"人事管理"),
    PAYMENT(4,"支付管理"),
    PLATFORM_BASE(5,"平台基础配置管理")
    ;

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    SystemMenuEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }

}
