package com.qualintech.taskcentre.controller.contract;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author yimzhu
 */
@Data
public class QueryReviewIfAllPassRequest {

    @NotNull
    private Long ownerId;
    @NotNull
    private Integer taskId;
    @NotNull
    private Integer taskState;

}
