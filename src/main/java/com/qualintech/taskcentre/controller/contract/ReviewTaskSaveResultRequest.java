package com.qualintech.taskcentre.controller.contract;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.ReviewState;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yimzhu
 */
@Data
public class ReviewTaskSaveResultRequest {
    /**
     * 执行人
     */
    @NotNull
    private Long ownerId;
    /**
     * 流程任务的模块（来料，NCR）
     */
    @NotNull
    private Module module;
    /**
     * 流程任务ID
     */
    @NotNull
    private Integer taskId;
    /**
     *
     */
    @NotNull
    private Integer taskType;
    /**
     *
     */
    @NotNull
    private Integer taskState;
    @EnumValue
    private ReviewState reviewState;

}
