package com.qualintech.taskcentre.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 委托任务用10xx表示
 * @author yimzhu
 */

@AllArgsConstructor
@Getter
public enum DelegateState {
    /**
     * 未完成
     */
    INCOMPLETE(1000,"未完成"),
    /**
     * 完成
     */
    COMPLETE(1010,"完成"),
    /**
     * 已转交
     */
    TRANSFERRED(1011,"已转交"),

    /**
     * 初始化 (FLOW TASK ONLY)
     */
    OPEN(0,"开启"),

    /**
     * 结束 (FLOW TASK ONLY)
     */
    CLOSE(1,"关闭");

    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
