package com.qualintech.taskcentre.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qualintech.taskcentre.enums.*;
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
public class Material {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * State 状态
     */
    @TableField(value = "state")
    private MaterialState state;

    /**
     * Module 模型
     */
    @TableField(value = "module")
    private Module module = Module.MATERIAL;

    /**
     * Owner 执行人
     */
    @TableField(value = "owner_id")
    private Long ownerId;

    /**
     * Insert Time 创建时间
     */
    @TableField(value = "insert_time", fill = FieldFill.INSERT)
    private LocalDateTime insertTime;

    /**
     * Complete Time 完成时间
     */
    @TableField(value = "complete_time")
    private LocalDateTime completeTime;

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

//    public static OrderBuilder builder() {
//        return new OrderBuilder();
//    }
}