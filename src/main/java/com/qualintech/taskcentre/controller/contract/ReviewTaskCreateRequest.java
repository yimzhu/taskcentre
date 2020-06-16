package com.qualintech.taskcentre.controller.contract;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yimzhu
 */
@Data
public class ReviewTaskCreateRequest {
    @NotNull(message = "ownerId，执行人ID列表不能为空")
    private List<Long> ownerId;
    @NotNull(message = "delegateTaskId，委托任务ID不能为空")
    private Integer delegateTaskId;
    @NotNull(message = "delegateType，委托类型不能为空")
    private Integer delegateTaskType;
    @NotNull(message = "delegateTaskState，委托任务状态不能为空")
    private Integer delegateTaskState;
    @NotNull(message = "expectTime，预期时间不能为空")
    private String expectTime;
}
