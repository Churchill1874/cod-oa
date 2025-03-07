package com.ent.codoa.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 库存操作类型枚举
 */
public enum OperationTypeEnum {
    STOCKIN("入库",0),
    STOCKOUT("出库",1),
    TOBERETURN("退货",2);


    @Getter
    @EnumValue
    @JsonValue
    private Integer code;

    @Getter
    private String name;

    OperationTypeEnum(String name,Integer code){
        this.name=name;
        this.code=code;
    }

    @Override
    public String toString(){
        return this.name+":"+this.code;
    }
}
