package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.enums.ReviewEvent;
import com.qualintech.taskcentre.enums.ReviewState;
import com.qualintech.taskcentre.service.IReviewTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * @author yimzhu
 */
@Slf4j
public class DelegateTaskStateMachine extends AbstractStateMachine<DelegateTaskStateMachine, ReviewState, ReviewEvent, ReviewTask> {

    private ApplicationContext applicationContext;

    public DelegateTaskStateMachine(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void afterTransitionCompleted(ReviewState fromState, ReviewState toState, ReviewEvent event, ReviewTask reviewTask) {
            reviewTask.setResult(toState);
            IReviewTaskService reviewService = applicationContext.getBean(IReviewTaskService.class);
            reviewService.updateById(reviewTask);
    }
}
