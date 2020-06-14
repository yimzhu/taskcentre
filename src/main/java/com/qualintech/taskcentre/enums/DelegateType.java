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
public enum DelegateType {
    /** 委托检测 */
    MATERIAL_TEST(110,"委托检测"),
    /** 结论判定 */
    MATERIAL_JUDGE(120,"结论判定"),
    /** 问题信息登记 */
    RECORD(210,"问题信息登记"),
    /** 问题处置方案 */
    ISSUE_SOLUTION(220,"问题处置方案"),
    /** 方案执行结果 */
    ISSUE_RESULT(221,"方案执行结果"),
    /** 方案执行结果 */
    ISSUE_VERIFICATION(222,"处置结果验证"),
    /** 方案执行结果 */
    SUSPICIONS_CHECK(230,"可疑品排查"),
    /** 方案执行结果 */
    SHORT_TERM_SOLUTION(240,"短期控制方案"),
    SHORT_TERM_RESULT(241,"方案执行结果"),
    SHORT_TERM_VERIFICATION(241,"方案执行结果");

    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
