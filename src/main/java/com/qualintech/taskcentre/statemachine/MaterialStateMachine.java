package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.service.IMaterialService;
import com.qualintech.taskcentre.service.impl.MaterialServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

import java.time.LocalDateTime;

@Slf4j
public class MaterialStateMachine extends AbstractStateMachine<MaterialStateMachine, MaterialState, MaterialEvent, Material> {

    private ApplicationContext applicationContext;

    public MaterialStateMachine(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void afterTransitionCompleted(MaterialState fromState, MaterialState toState, MaterialEvent event, Material material) {
        material.setState(toState);
        material.setCompleteTime(LocalDateTime.now());
        MaterialServiceImpl materialService = applicationContext.getBean(MaterialServiceImpl.class);
        boolean result = materialService.updateById(material);
        log.info("变更流程状态，从【" + fromState.getName() + "】到【" + toState.getName() + "】:" + result);
    }
}
