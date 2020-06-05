package com.qualintech.taskcentre.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实现 IEnum<String> 是为了 Order 的 state 属性在Mybatis中能直接使用此枚举
 */
@AllArgsConstructor
@Getter
public enum Module {
    MATERIAL(0,"模块1"),
    NCR(1,"模块2"),
//    DONE,
//    AUDITING,
//    AUDIT_PASS,
//    AUDIT_REJECT,
//    RECALLED;
;
    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
