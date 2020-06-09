package com.qualintech.taskcentre.controller.contract;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yimzhu
 */
@Data
public class ReviewTaskCreateRequest {
    @NotNull
    private List<Long> ownerId;
    @NotNull
    private Integer taskId;
    @NotNull
    private Integer taskType;
    @NotNull(message = "taskState不能为空")
    private Integer taskState;
}
