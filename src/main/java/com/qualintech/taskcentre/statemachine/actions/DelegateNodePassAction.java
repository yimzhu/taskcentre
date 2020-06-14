package com.qualintech.taskcentre.statemachine.actions;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.DelegateEvent;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.service.impl.DelegateTaskServiceImpl;
import com.qualintech.taskcentre.service.impl.MaterialServiceImpl;
import com.qualintech.taskcentre.statemachine.DelegateTaskStateMachine;
import com.qualintech.taskcentre.statemachine.DelegateTaskStateMachineEngine;
import com.qualintech.taskcentre.statemachine.MaterialStateMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousAction;

/**
 * @author yimzhu
 */
@Slf4j
@Component
public class DelegateNodePassAction extends AnonymousAction<DelegateTaskStateMachine, DelegateState, DelegateEvent, DelegateTask> {
    @Autowired
    private DelegateTaskServiceImpl delegateTaskService;

    @Override
    public void execute(DelegateState fromState, DelegateState toState, DelegateEvent event, DelegateTask delegateTask, DelegateTaskStateMachine delegateTaskStateMachine) {
        //执行重置委托和审核状态，以进入下一个流程
        delegateTask.setReviewFlag(0);
        UpdateWrapper<DelegateTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", delegateTask.getId());
        boolean sqlResult = delegateTaskService.update(delegateTask,updateWrapper);
        log.info("委托任务【" + delegateTask.getId() + "】,委托类型【" + delegateTask.getDelegateType() + "】的审核状态重置结果:" + sqlResult);
    }
}
