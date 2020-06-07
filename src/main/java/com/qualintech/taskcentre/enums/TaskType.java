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
public enum TaskType {
    FLOW(0,"流程"),
    DELEGATE(1,"委托");

    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
