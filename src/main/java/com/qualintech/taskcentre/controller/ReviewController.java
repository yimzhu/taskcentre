package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.QueryReviewIfAllPassRequest;
import com.qualintech.taskcentre.controller.contract.ReviewTaskCreateRequest;
import com.qualintech.taskcentre.controller.contract.ReviewTaskSaveResultRequest;
import com.qualintech.taskcentre.enums.TaskType;
import com.qualintech.taskcentre.service.impl.ReviewTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    @Autowired
    private ReviewTaskServiceImpl reviewTaskService;

    @PostMapping("/create")
    public void createReview(@RequestBody @Valid ReviewTaskCreateRequest request) {
        reviewTaskService.create(request.getOwnerId(),request.getTaskId(),request.getTaskType(),request.getTaskState());
    }

    @PostMapping("/save/result")
    public Boolean saveReviewForFlow(@RequestBody @Valid ReviewTaskSaveResultRequest request) {
        if(request.getModule()!=null){
            log.info("流程任务【" + request.getModule().getName() + "】保存审核记录");
            assert request.getTaskType() == TaskType.FLOW.getCode();
            return reviewTaskService.saveReviewForFlow(request.getOwnerId(), request.getTaskId(), request.getModule(), request.getTaskState(), request.getReviewState());
        }else{
            log.info("委托任务【" + request.getModule().getName() + "】保存审核记录");
            return reviewTaskService.saveReviewForDelegate(request.getOwnerId(), request.getTaskId(), request.getTaskState(), request.getReviewState());
        }
    }

    @PostMapping("/query/incomplete")
    public int queryIsReviewAllPass(@RequestBody @Valid QueryReviewIfAllPassRequest request) throws Exception {
        return reviewTaskService.queryInCompleteCount(request.getOwnerId(),request.getTaskId(),request.getTaskState());
    }


}
