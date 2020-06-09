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
     * 0-δͨ����ʧ��+����ˣ� 1-ͨ��
     */
    private Integer result;

}
