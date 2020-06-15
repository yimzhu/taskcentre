package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.Ncr;
import com.qualintech.taskcentre.enums.NcrEvent;
import com.qualintech.taskcentre.enums.NcrState;
import com.qualintech.taskcentre.service.impl.NcrServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

import java.time.LocalDateTime;

@Slf4j
public class NcrStateMachine extends AbstractStateMachine<NcrStateMachine, NcrState, NcrEvent, Ncr> {

    private ApplicationContext applicationContext;

    public NcrStateMachine(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void afterTransitionCompleted(NcrState fromState, NcrState toState, NcrEvent event, Ncr ncr) {
        ncr.setState(toState);
        ncr.setCompleteTime(LocalDateTime.now());
        NcrServiceImpl ncrService = applicationContext.getBean(NcrServiceImpl.class);
        boolean result = ncrService.updateById(ncr);
        log.info("变更流程状态，从【" + fromState.getName() + "】到【" + toState.getName() + "】:" + result);
    }
}
