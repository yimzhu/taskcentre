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
    INIT(100,"待派发"),
    PROCESSING(110,"处理中"),
    DONE(120,"完成"),
    AUDITING(130,"待审核"),
    AUDIT_PASS(140,"审核通过"),
    AUDIT_REJECT(150,"审核驳回"),
    RECALLED(160,"已召回"),
    OVERDUE(170,"逾期中");

    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
