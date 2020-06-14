package com.qualintech.taskcentre.enums;

/**
 * @author yimzhu
 */

public enum DelegateEvent {
    DISPATCH,
    COMPLETE_WITHOUT_AUDIT,
    COMPLETE_WITH_AUDIT,
    PASS_AUDIT,
    REJECT_AUDIT,
    RECALL;
}
