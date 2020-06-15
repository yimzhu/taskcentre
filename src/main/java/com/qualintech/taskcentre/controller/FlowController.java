package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.FlowTaskCreateRequest;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.service.impl.MaterialServiceImpl;
import com.qualintech.taskcentre.statemachine.MaterialStateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yimzhu
 */
@RestController
@RequestMapping("flow")
public class FlowController {
    @Autowired
    private MaterialServiceImpl materialService;

    @Autowired
    private MaterialStateMachineEngine materialStateMachineEngine;


    @GetMapping("/{id}")
    public Material get(@PathVariable long id) {
        return materialService.getById(id);
    }

    @PostMapping("")
    public Material create(@RequestBody FlowTaskCreateRequest request) {
        return delegateTaskService.create(request.getOwnerId());
    }

    @GetMapping("/{id}/dispatch")
    public Material dispatch(@PathVariable long id) throws Exception {
        DelegateTask delegateTask = delegateTaskService.getById(id);
        delegateStateMachineEngine.fire(delegateTask.getState(), delegateTaskService.DISPATCH, material);
        return material;
    }

//    @GetMapping("/{id}/refund")
//    public Material materialRefund(@PathVariable long id) throws Exception {
//        Material material = materialService.getById(id);
//        materialStateMachineEngine.fire(material.getState(), OrderEvent.DELIVER, material);
//        return order;
//    }


}
