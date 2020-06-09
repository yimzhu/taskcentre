package com.qualintech.taskcentre.controller.contract;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author yimzhu
 */
@Data
public class QueryReviewResultRequest {

    @NotNull
    private Long ownerId;
    @NotNull
    private Integer taskId;
    @NotNull
    private Integer taskState;
    @NotNull
    /**
     * 0-未通过（失败+待审核） 1-通过
     */
    private Integer result;

}
