package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.QueryReviewResultRequest;
import com.qualintech.taskcentre.controller.contract.ReviewTaskCreateRequest;
import com.qualintech.taskcentre.controller.contract.ReviewTaskSaveResultRequest;
import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.service.impl.ReviewTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/review")
@Slf4j
/** 注意！1) 如果想在参数中使用 @NotNull 这种注解校验，就必须在类上添加 @Validated；2) 如果方法中的参数是对象类型，则必须要在参数对象前面添加 @Validated */
@Validated
/**
 * @author yimzhu
 */
public class ReviewController {

    @Autowired
    private ReviewTaskServiceImpl reviewTaskService;

    @PostMapping("")
    public boolean createReview(@RequestBody @Validated ReviewTaskCreateRequest request) {
        return reviewTaskService.create(request.getOwnerId(),request.getTaskId(),request.getTaskState());
    }

    @PostMapping("/update")
    public Boolean saveReviewForFlow(@RequestBody @Valid ReviewTaskSaveResultRequest request) {
        log.info("委托任务【" + request.getModule().getName() + "】保存审核记录");
        return reviewTaskService.saveReviewForDelegate(request.getOwnerId(), request.getTaskId(), request.getTaskState(), request.getReviewState());

    }

    @PostMapping("/query")
    public List<ReviewTask> queryReviewResultCount(@RequestBody @Valid QueryReviewResultRequest request) throws Exception {
        return reviewTaskService.queryReviewRecords(request.getOwnerId(),request.getTaskId(),request.getTaskState(),request.getResult());
    }
}
