package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.DelegateTaskCreateRequest;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.enums.DelegateEvent;
import com.qualintech.taskcentre.enums.DelegateType;
import com.qualintech.taskcentre.mapper.DelegateTaskMapper;
import com.qualintech.taskcentre.service.impl.DelegateTaskServiceImpl;
import com.qualintech.taskcentre.statemachine.DelegateStateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yimzhu
 */
@RestController
@RequestMapping("delegate")
public class DelegateController {
    @Autowired
    private DelegateTaskServiceImpl delegateTaskService;

    @Autowired
    private DelegateStateMachineEngine delegateStateMachineEngine;

    @Autowired
    private DelegateTaskMapper delegateTaskMapper;

    @GetMapping("/{id}")
    public DelegateTask get(@PathVariable long id) {
        return delegateTaskService.getById(id);
    }

    @PostMapping("")
    public Long create(@RequestBody DelegateTaskCreateRequest request) {
        DelegateType delegateType = DelegateType.getDelegateType(request.getDelegateType());
        return delegateTaskService.create(request.getOwnerId(),request.getFlowTaskId(),delegateType, request.getExpectTime());
    }

    @GetMapping("/{id}/{event}")
    public DelegateTask trigger(@PathVariable long id, @PathVariable long event) {
        DelegateTask delegateTask = delegateTaskService.getById(id);
        DelegateEvent delegateEvent = DelegateEvent.valueOf(String.valueOf(event));
        delegateStateMachineEngine.fire(delegateTask.getState(), delegateEvent, delegateTask);
        return delegateTask;
    }

}
