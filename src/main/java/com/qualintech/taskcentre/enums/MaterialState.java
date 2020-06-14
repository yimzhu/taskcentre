package com.qualintech.taskcentre.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 来料的流程
 * @author yimzhu
 */

@AllArgsConstructor
@Getter
public enum MaterialState {
    BASIC_INFO(10,"基本信息"),
    INSPECTION_RECORDS(20,"检验记录"),
    CONCLUSION(30,"结论判定"),
    FINAL_REVIEW(40,"终审");

    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
