package com.qualintech.taskcentre.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yimzhu
 */
@AllArgsConstructor
@Getter
public enum Module {
    MATERIAL(0,"来料管理"),
    NCR(1,"问题管理"),
    Order(9,"订单管理");

    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
