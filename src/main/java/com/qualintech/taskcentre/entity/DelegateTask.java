package com.qualintech.taskcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.DelegateType;
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
@TableName(value = "delegate_task")
public class DelegateTask {
    /**
     * 委托任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 委托状态
     */
    @TableField(value = "state")
    private DelegateState state;

    /**
     * 委托类型
     */
    @TableField(value = "module")
    private DelegateType delegateType;

    /**
     * 挂钩的流程任务ID
     */
    @TableField(value = "flow_task_id")
    private Long flowTaskId;

    /**
     * 当前执行人
     */
    @TableField(value = "owner_id")
    private Long ownerId;

    /**
     * 创建时间
     */
    @TableField(value = "insert_time")
    private LocalDateTime insertTime;

    /**
     * 完成时间
     */
    @TableField(value = "complete_time")
    private LocalDateTime completeTime;

    /**
     * 期望完成时间
     */
    @TableField(value = "overdue_time")
    private LocalDateTime overdueTime;

    /**
     * 逾期标记
     */
    @TableField(value = "overdue_flag")
    private Integer overdueFlag;

    /**
     * 审核状态
     */
    @TableField(value = "review_state")
    private ReviewState reviewState;

    /**
     * 审核标记
     */
    @TableField(value = "review_flag")
    private Integer reviewFlag;

    /**
     * 是否启用
     */
    @TableField(value = "is_active")
    private Integer isActive;

    public static final String COL_ID = "id";

    public static final String COL_STATE = "state";

//    public static OrderBuilder builder() {
//        return new OrderBuilder();
//    }
}