package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum CustomerWorkOrderStatusEnum {

    PENDING(0, "待处理"),
    PROCESSING(1,"处理中"),
    FINISH(2,"以完结");

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    CustomerWorkOrderStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }


}
