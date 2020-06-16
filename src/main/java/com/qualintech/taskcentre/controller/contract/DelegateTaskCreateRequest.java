package com.qualintech.taskcentre.controller.contract;

import com.qualintech.taskcentre.enums.DelegateType;
import com.qualintech.taskcentre.enums.Module;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author yimzhu
 */
@Data
public class DelegateTaskCreateRequest {
    @NotNull(message = "ownerId，执行人不能为空")
    private Long ownerId;
    @NotNull(message = "flowTaskId，流程任务ID不能为空")
    private Long flowTaskId;
    @NotNull(message = "module，模块ID不能为空")
    private Module module;
    @NotNull(message = "delegateType，委托类型不能为空")
    private int delegateType;
    @NotNull(message = "expectTime，预期时间不能为空")
    private String expectTime;
}
