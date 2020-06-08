package com.qualintech.taskcentre.controller.contract;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.ReviewState;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yimzhu
 */
@Data
public class ReviewTaskSaveResultRequest {
    /**
     * 执行人
     */
    private Long ownerId;
    /**
     * 流程任务的模块（来料，NCR）
     */

    private Module module;
    /**
     * 流程任务ID
     */
    private Integer taskId;
    /**
     *
     */
    private Integer taskType;
    /**
     *
     */
    private Integer taskSate;
    @EnumValue
    private ReviewState reviewState;

}
