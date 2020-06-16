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
    INIT(100,"待派发"),
    PROCESSING(110,"处理中"),
    DONE(120,"完成"),
    AUDITING(130,"待审核"),
    AUDIT_PASS(140,"审核通过"),
    AUDIT_REJECT(150,"审核驳回"),
    RECALLED(160,"已召回"),

    /**
     * 初始化 (FLOW TASK ONLY)
     */
    OPEN(1,"开启"),

    /**
     * 结束 (FLOW TASK ONLY)
     */
    CLOSE(0,"关闭");

    @JsonValue
    @EnumValue
    private int code;
    private String name;

    public static DelegateState getDelegateState(int code){
        for(DelegateState delegateState:DelegateState.values()){
            return delegateState;
        }
        return null;
    }
}
