package com.qualintech.taskcentre.statemachine.actions;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.service.impl.MaterialServiceImpl;
import com.qualintech.taskcentre.statemachine.MaterialStateMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousAction;

@Slf4j
@Component
public class MeterialNodePassAction extends AnonymousAction<MaterialStateMachine, MaterialState, MaterialEvent, Material> {

    @Autowired
    private MaterialServiceImpl materialService;

    @Override
    public void execute(MaterialState materialState, MaterialState s1, MaterialEvent event, Material material, MaterialStateMachine materialStateMachine) {
        boolean delegateFlag = material.getDelegateFlag()==1?true:false;
        log.info("�Ƿ����ί������" + delegateFlag);

        if(delegateFlag == true){
            //ִ������ί�к����״̬���Խ�����һ������
            Material clearState = new Material();
            clearState.setDelegateFlag(0);
            clearState.setReviewFlag(0);
            UpdateWrapper<Material> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", material.getId());
            boolean sqlResult = materialService.update(clearState,updateWrapper);
            log.info("���������ί�к����״̬���ý����" + sqlResult);
            assert sqlResult;
        }
    }
}
