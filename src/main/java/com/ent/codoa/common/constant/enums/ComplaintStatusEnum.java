package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ComplaintStatusEnum {

    ALREADY_COMPLAINED(0, "以投诉"),
    PROCESSED(1, "以受理");

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    ComplaintStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }


}
