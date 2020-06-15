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
public enum NcrState {
    /** 问题信息登记 */
    RECORD(10,"问题信息登记"),
    /** 委托处理问题 */
    RESOLVING(20,"委托处理问题"),
    /** 审核结果 */
    REVIEWING(21,"审核结果"),
    /** 已关闭 */
    CLOSED(22,"已关闭");

    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
