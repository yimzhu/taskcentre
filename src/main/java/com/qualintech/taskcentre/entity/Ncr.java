package com.qualintech.taskcentre.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qualintech.taskcentre.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "flow_task")
public class Ncr {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * State 状态
     */
    @TableField(value = "state")
    private NcrState state;


    /**
     * Module 模型
     */
    @TableField(value = "module")
    private Module module = Module.NCR;

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
     * Delegate State 委托状态
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

//    public static OrderBuilder builder() {
//        return new OrderBuilder();
//    }
}