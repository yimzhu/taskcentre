package com.qualintech.taskcentre.controller.contract;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yimzhu
 */
@Data
public class ReviewTaskCreateRequest {
    private List<Long> ownerId;
    private Integer taskId;
    private Integer taskType;
//    @NotNull
//    private Integer taskState;
    @NotNull
    private String status;
}
