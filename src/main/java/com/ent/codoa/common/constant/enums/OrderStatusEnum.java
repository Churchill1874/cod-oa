package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum OrderStatusEnum {

    PENDING(0, "待处理"),
    PROCESSING(1, "处理中"),
    SUCCESS(2,"交易成功"),
    FAIL(3,"交易失败")
    ;

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    OrderStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }


}
