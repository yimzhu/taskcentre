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
public enum MaterialState {
    INIT(0,"待派发"),
    PROCESSING(10,"处理中"),
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
