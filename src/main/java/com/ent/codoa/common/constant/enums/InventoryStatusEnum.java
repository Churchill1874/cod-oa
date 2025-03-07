package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 库存状态枚举
 */
public enum InventoryStatusEnum {

    TOBESOLD("待销售",0),
    SOLDED("已销售",1),
    RETURNED("已退货",2);

    @Getter
    @EnumValue
    @JsonValue
    private Integer code;

    @Getter
    private String name;

    InventoryStatusEnum(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }

}
