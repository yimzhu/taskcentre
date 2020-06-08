package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.ReviewTaskCreateRequest;
import com.qualintech.taskcentre.controller.contract.ReviewTaskSaveResultRequest;
import com.qualintech.taskcentre.enums.TaskType;
import com.qualintech.taskcentre.service.impl.ReviewTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
//@RequestMapping("/review")
@Slf4j
public class ReviewController {

    @Autowired
    private ReviewTaskServiceImpl reviewTaskService;

    @PostMapping("/create")
    public void createReview(@RequestBody @Valid ReviewTaskCreateRequest request) {
        System.out.println(request.toString());
        System.out.println(111);


        System.out.println(222);
//        reviewTaskService.create(request.getOwnerId(),request.getTaskId(),request.getTaskType(),request.getTaskState());
    }

    @PostMapping("/save/result")
    public Boolean saveReviewForFlow(@Valid @RequestBody ReviewTaskSaveResultRequest request) {
        if(request.getModule()!=null){
            log.info("��������" + request.getModule().getName() + "�����...");
            //������������״̬���棬module��Ϊ�գ�tasktypeΪ0-flow
            assert request.getTaskType() == TaskType.FLOW.getCode();
            return reviewTaskService.saveReviewForFlow(request.getOwnerId(), request.getTaskId(), request.getModule(), request.getTaskSate(), request.getReviewState());
        }else{
            log.info("ί������[" + request.getModule().getName() + "]���...");
            //ί����������״̬���棬moduleΪ�գ�TasktypeΪ1-delegate
            return reviewTaskService.saveReviewForDelegate(request.getOwnerId(), request.getTaskId(), request.getTaskSate(), request.getReviewState());
        }
    }

//    @GetMapping("/{id}/refund")
//    public Material materialRefund(@PathVariable long id) throws Exception {
//        Material material = materialService.getById(id);
//        materialStateMachineEngine.fire(material.getState(), OrderEvent.DELIVER, material);
//        return order;
//    }


}
