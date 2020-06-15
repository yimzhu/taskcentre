package com.qualintech.taskcentre.statemachine.actions;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.entity.Ncr;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.enums.NcrEvent;
import com.qualintech.taskcentre.enums.NcrState;
import com.qualintech.taskcentre.service.impl.MaterialServiceImpl;
import com.qualintech.taskcentre.service.impl.NcrServiceImpl;
import com.qualintech.taskcentre.statemachine.MaterialStateMachine;
import com.qualintech.taskcentre.statemachine.NcrStateMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousAction;

/**
 * @author yimzhu
 */
@Slf4j
@Component
public class NcrNodePassAction extends AnonymousAction<NcrStateMachine, NcrState, NcrEvent, Ncr> {
    @Autowired
    private NcrServiceImpl ncrService;

    @Override
    public void execute(NcrState fromState, NcrState toState, NcrEvent event, Ncr ncr, NcrStateMachine ncrStateMachine) {
        //执行重置委托和审核状态，以进入下一个流程
        ncr.setDelegateFlag(0);
        UpdateWrapper<Ncr> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", ncr.getId());
        updateWrapper.eq("state", ncr.getState());
        boolean sqlResult = ncrService.update(ncr,updateWrapper);
        log.info("问题任务【" + ncr.getId() + "】,创建人【" + ncr.getOwnerId() + "】的委托状态重置结果:" + sqlResult);
    }
}
