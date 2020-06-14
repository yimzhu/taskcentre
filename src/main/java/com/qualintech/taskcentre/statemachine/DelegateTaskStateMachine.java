package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.enums.DelegateEvent;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.ReviewEvent;
import com.qualintech.taskcentre.enums.ReviewState;
import com.qualintech.taskcentre.service.IDelegateTaskService;
import com.qualintech.taskcentre.service.IReviewTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * @author yimzhu
 */
@Slf4j
public class DelegateTaskStateMachine extends AbstractStateMachine<DelegateTaskStateMachine, DelegateState, DelegateEvent, DelegateTask> implements StateMachine<DelegateTaskStateMachine, DelegateState, DelegateEvent, DelegateTask> {

    private ApplicationContext applicationContext;

    public DelegateTaskStateMachine(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 完成状态的转变
     * @param fromState 从
     * @param toState 到
     * @param event 事件
     * @param delegateTask 代理任务
     */
    @Override
    protected void afterTransitionCompleted(DelegateState fromState, DelegateState toState, DelegateEvent event, DelegateTask delegateTask) {
        delegateTask.setState(toState);
        IDelegateTaskService delegateTaskService = applicationContext.getBean(IDelegateTaskService.class);
        delegateTaskService.updateById(delegateTask);
    }
}
