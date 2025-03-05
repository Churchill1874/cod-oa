package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum PaymentStatusEnum {

    UNPAID(0, "未支付"),
    PROCESSING(1, "支付中"),
    PAID(2,"以支付")
    ;

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    PaymentStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }


}
