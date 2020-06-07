package com.qualintech.taskcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qualintech.taskcentre.enums.DelegateState;
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
@TableName(value = "flow_task")
public class FlowTask {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * State 状态
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * Module 模型
     */
    @TableField(value = "module")
    private Module module;

    /**
     * Owner 执行人
     */
    @TableField(value = "owner_id")
    private Long ownerId;

    /**
     * Insert Time 创建时间
     */
    @TableField(value = "insert_time")
    private LocalDateTime insertTime;

    /**
     * Complete Time 完成时间
     */
    @TableField(value = "complete_time")
    private LocalDateTime completeTime;

    /**
     * Overdue Time 逾期时间
     */
    @TableField(value = "overdue_time")
    private LocalDateTime overdueTime;

    /**
     * Overdue Flag 逾期标记
     */
    @TableField(value = "overdue_flag")
    private Integer overdueFlag;

    /**
     * Review State 审核状态 2000-未审核，2010-审核通过，2011-审核失败
     */
    @TableField(value = "review_state")
    private ReviewState reviewState;

    /**
     * Review Flag 审核标记，是否需要审核
     */
    @TableField(value = "review_flag")
    private Integer reviewFlag;

    /**
     * Delegate State 委托状态 1000-未完成，1010-完成，1011-已转交, 0-委托进行中(FlowTask用), 1-委托结束(FlowTask用), 999-未知
     */
    @TableField(value = "delegate_state")
    private DelegateState delegateState;

    /**
     * Delegate Flag 委托标记，是否委托
     */
    @TableField(value = "delegate_flag")
    private Integer delegateFlag;

    public static final String COL_ID = "id";

    public static final String COL_STATE = "state";
}
