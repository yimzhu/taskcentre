package com.qualintech.taskcentre.controller.contract;

import com.qualintech.taskcentre.enums.TaskType;
import lombok.Data;

import java.util.List;

/**
 * @author yimzhu
 */
@Data
public class ReviewTaskCreateRequest {
    private List<Long> ownerId;
    private Integer taskId;
    private Integer taskType;
    private Integer taskSate;
}
