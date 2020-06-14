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
    /** 问题处置方案 */
    ISSUE_SOLUTION(20,"问题处置方案"),
    /** 方案执行结果 */
    ISSUE_RESULT(21,"方案执行结果"),
    /** 方案执行结果 */
    ISSUE_VERIFICATION(22,"处置结果验证"),
    /** 方案执行结果 */
    SUSPICIONS_CHECK(30,"可疑品排查"),
    /** 方案执行结果 */
    SHORT_TERM_SOLUTION(40,"短期控制方案"),
    SHORT_TERM_RESULT(41,"方案执行结果"),
    SHORT_TERM_VERIFICATION(41,"方案执行结果");

    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
