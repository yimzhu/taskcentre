package com.qualintech.taskcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "review_task")
public class ReviewTask {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Task State
     */
    @TableField(value = "out_task_state")
    private Integer outTaskState;

    /**
     * Task ID
     */
    @TableField(value = "out_task_id")
    private Integer outTaskId;

    /**
     * Module 模型
     */
    @TableField(value = "module")
    private Module module;

    /**
     * Owner
     */
    @TableField(value = "owner_id")
    private Long ownerId;

    /**
     * Review Result
     */
    @TableField(value = "result")
    private ReviewState result;

    /**
     * Insert Time
     */
    @TableField(value = "insert_time")
    private LocalDateTime insertTime;

    /**
     * Complete Time
     */
    @TableField(value = "complete_time")
    private LocalDateTime completeTime;

    /**
     * Overdue Time
     */
    @TableField(value = "overdue_time")
    private LocalDateTime overdueTime;

    /**
     * Overdue Flag
     */
    @TableField(value = "overdue_flag")
    private Integer overdueFlag;

    /**
     * Is Active
     */
    @TableField(value = "is_active")
    private Integer isActive;

    public static final String COL_ID = "id";

    public static final String COL_STATE = "state";

}