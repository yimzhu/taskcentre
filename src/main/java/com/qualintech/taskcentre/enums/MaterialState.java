package com.qualintech.taskcentre.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 来料用1xx表示状态
 * @author yimzhu
 */

@AllArgsConstructor
@Getter
public enum MaterialState {


    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
