package com.qualintech.taskcentre.controller.contract;

import com.qualintech.taskcentre.enums.Module;
import lombok.Data;

/**
 * @author yimzhu
 */
@Data
public class FlowTaskCreateRequest {
    private Long ownerId;
    private Module module;
}
