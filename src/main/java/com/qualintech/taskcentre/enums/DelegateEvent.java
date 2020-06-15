package com.qualintech.taskcentre.enums;

/**
 * @author yimzhu
 */

public enum DelegateEvent {
    /**
     * 派发
     */
    DISPATCH,
    /**
     * 完成(无审核)
     */
    COMPLETE_WITHOUT_AUDIT,
    /**
     * 完成(有审核)
     */
    COMPLETE_WITH_AUDIT,
    /**
     * 审核通过
     */
    PASS_AUDIT,
    /**
     * 审核驳回
     */
    REJECT_AUDIT,
    /**
     * 召回
     */
    RECALL,
    /**
     * 重开
     */
    RESET;
}
