package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.service.IMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

@Slf4j
public class MaterialStateMachine extends AbstractStateMachine<MaterialStateMachine, MaterialState, MaterialEvent, Material> {

    private ApplicationContext applicationContext;

    public MaterialStateMachine(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void afterTransitionCompleted(MaterialState fromState, MaterialState toState, MaterialEvent event, Material material) {
            material.setState(toState);
            System.out.println("aftertranstioncomplete");
            IMaterialService materialService = applicationContext.getBean(IMaterialService.class);
            materialService.updateById(material);
    }
}
