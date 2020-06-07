package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.ReviewTaskCreateRequest;
import com.qualintech.taskcentre.mapper.FlowTaskMapper;
import com.qualintech.taskcentre.service.impl.ReviewTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("review")
public class ReviewController {
    @Autowired
    private FlowTaskMapper flowTaskMapper;
    @Autowired
    private ReviewTaskServiceImpl reviewTaskService;

    @PostMapping("")
    public void createReview(@RequestBody ReviewTaskCreateRequest request) {
        reviewTaskService.create(request.getOwnerId(),request.getTaskId(),request.getTaskType(),request.getTaskSate());
    }

    @PostMapping("")
    public void saveReview(@RequestBody ReviewTaskCreateRequest request) {
        reviewTaskService.create(request.getOwnerId(),request.getTaskId(),request.getTaskType(),request.getTaskSate());
    }

//    @GetMapping("/{id}/refund")
//    public Material materialRefund(@PathVariable long id) throws Exception {
//        Material material = materialService.getById(id);
//        materialStateMachineEngine.fire(material.getState(), OrderEvent.DELIVER, material);
//        return order;
//    }


}
