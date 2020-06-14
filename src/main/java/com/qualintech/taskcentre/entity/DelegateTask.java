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
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * state
     */
    @TableField(value = "state")
    private DelegateState state;

    /**
     * state
     */
    @TableField(value = "module")
    private DelegateType delegateType;

    @TableField(value = "flow_task_id")
    private Long flowTaskId;

    @TableField(value = "owner_id")
    private Long ownerId;

    @TableField(value = "insert_time")
    private LocalDateTime insertTime;

    @TableField(value = "complete_time")
    private LocalDateTime completeTime;

    @TableField(value = "overdue_time")
    private LocalDateTime overdueTime;

    @TableField(value = "overdue_flag")
    private Integer overdueFlag;

    @TableField(value = "review_state")
    private ReviewState reviewState;

    @TableField(value = "review_flag")
    private Integer reviewFlag;

    @TableField(value = "is_active")
    private Integer isActive;

    public static final String COL_ID = "id";

    public static final String COL_STATE = "state";

//    public static OrderBuilder builder() {
//        return new OrderBuilder();
//    }
}