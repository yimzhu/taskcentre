package com.qualintech.taskcentre.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态用20xx表示
 * @author yimzhu
 */

@AllArgsConstructor
@Getter
public enum ReviewState {
    /**
     * 待审核
     */
    IN_REVIEW(2000,"待审核"),
    /**
     * 审核通过
     */
    REVIEW_PASS(2010,"审核通过"),
    /**
     * 审核失败
     */
    REVIEW_FAIL(2011,"审核拒绝"),

    /**
     * 初始化 (REVIEW TASK ONLY)
     */
    OPEN(0,"开启"),

    /**
     * 结束 (REVIEW TASK ONLY)
     */
    CLOSE(1,"关闭");
//    DELIVERING,  //配送中
//    RECEIVED,    //已签收
//    CLOSED;
;
    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
