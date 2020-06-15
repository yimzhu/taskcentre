package com.qualintech.taskcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qualintech.taskcentre.enums.DelegateType;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * @author yimzhu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "transfer_history")
public class TransferHistory {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联任务ID
     */
    @TableField(value = "task_id")
    private Long delegateTaskId;

    /**
     * 任务模型
     */
    @TableField(value = "module")
    private DelegateType delegateType;

    /**
     * State 状态, 对应Material等表中State状态
     */
    @TableField(value = "task_state")
    private Integer taskState;

    /**
     * Previous Owner 当前执行人
     */
    @TableField(value = "p_owner")
    private Long oldOwnerId;

    /**
     * Next Owner 目标执行人
     */
    @TableField(value = "n_owner")
    private Long newOwnerId;

    /**
     * Insert Time 创建时间
     */
    @TableField(value = "insert_time")
    private ZonedDateTime insertTime;

    public static final String COL_ID = "id";

    public static final String COL_STATE = "state";

//    public static OrderBuilder builder() {
//        return new OrderBuilder();
//    }
}