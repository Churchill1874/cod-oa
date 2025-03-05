package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ExploreProgressStatus {

    NOT_CONTACT(1, "未接触"),
    IN_COMMUNICATION(2, "沟通中"),
    TRADED(3,"以交易");

    @Getter
    @EnumValue
    @JsonValue
    private int code;

    @Getter
    private String name;

    ExploreProgressStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }
}
