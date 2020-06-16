package com.qualintech.taskcentre.controller.contract;

import com.qualintech.taskcentre.enums.DelegateType;
import com.qualintech.taskcentre.enums.Module;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yimzhu
 */
@Data
public class DelegateTaskCreateRequest {
    private Long ownerId;
    private Long flowTaskId;
    private Module module;
    private int delegateType;
    private String expectTime;
}
