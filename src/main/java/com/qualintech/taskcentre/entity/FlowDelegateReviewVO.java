package com.qualintech.taskcentre.entity;

import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.DelegateType;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.ReviewState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author yimzhu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowDelegateReviewVO {
    private Long id;

    /**
     * State 状态
     */
    private Integer flowState;

    /**
     * Module 模型
     */
    private Module module;

    /**
     * Owner 执行人
     */
    private Long ownerId;

    /**
     * Insert Time 创建时间
     */
    private LocalDateTime insertTime;

    /**
     * Complete Time 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * Delegate State 委托状态 0-委托进行中(FlowTask用), 1-委托结束(FlowTask用), 999-未知
     */
    private DelegateState delegateState;

    /**
     * Delegate Flag 委托标记，是否委托
     */
    private Integer delegateFlag;
    /**
     * 委托任务ID
     */
    private Long delegateTaskId;

    /**
     * 委托状态
     */
    private DelegateState state;

    /**
     * 委托类型
     */
    private DelegateType delegateType;

    /**
     * 挂钩的流程任务ID
     */
    private Long flowTaskId;

    /**
     * 当前执行人
     */
    private Long flowOwnerId;

    /**
     * 创建时间
     */
    private LocalDateTime delegateInsertTime;

    /**
     * 完成时间
     */
    private LocalDateTime delegateCompleteTime;

    /**
     * 期望完成时间
     */
    private LocalDateTime overdueTime;

    /**
     * 逾期标记
     */
    private Integer overdueFlag;

    /**
     * 审核状态
     */
    private ReviewState reviewState;

    /**
     * 审核标记
     */
    private Integer reviewFlag;

    /**
     * 是否启用
     */
    private Integer delegateIsActive;

    private Long reviewId;

    /**
     * Task State
     */
    private Integer outTaskState;

    /**
     * Task ID
     */
    private Integer outTaskId;

    /**
     * Module 模型
     */
    private Module reviewModule;

    /**
     * Owner
     */
    private Long reviewOwnerId;

    /**
     * Review Result
     */
    private ReviewState result;

    /**
     * Insert Time
     */
    private LocalDateTime reviewInsertTime;

    /**
     * Complete Time
     */
    private LocalDateTime reviewCompleteTime;

    /**
     * Overdue Time
     */
    private LocalDateTime reviewOverdueTime;

    /**
     * Overdue Flag
     */
    private Integer reviewOverdueFlag;

    /**
     * Is Active
     */
    private Integer reviewIsActive;

}