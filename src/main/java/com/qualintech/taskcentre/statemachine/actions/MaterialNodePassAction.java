package com.qualintech.taskcentre.statemachine.actions;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.service.impl.MaterialServiceImpl;
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
public class MaterialNodePassAction extends AnonymousAction<MaterialStateMachine, MaterialState, MaterialEvent, Material> {
    @Autowired
    private MaterialServiceImpl service;

    @Override
    public void execute(MaterialState fromState, MaterialState toState, MaterialEvent event, Material material, MaterialStateMachine materialStateMachine) {
        //执行重置委托和审核状态，以进入下一个流程
        material.setReviewFlag(0);
        material.setDelegateFlag(0);
        UpdateWrapper<Material> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", material.getId());
        boolean sqlResult = service.update(material,updateWrapper);
        log.info("来料任务的委托和审核状态重置结果:" + sqlResult);
    }
}
