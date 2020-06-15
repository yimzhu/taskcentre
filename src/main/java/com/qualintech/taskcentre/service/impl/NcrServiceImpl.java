package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.FlowTask;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.entity.Ncr;
import com.qualintech.taskcentre.entity.Order;
import com.qualintech.taskcentre.enums.*;
import com.qualintech.taskcentre.mapper.FlowTaskMapper;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.mapper.NcrMapper;
import com.qualintech.taskcentre.service.INcrService;
import com.qualintech.taskcentre.statemachine.MaterialStateMachineEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author yimzhu
 */
@Slf4j
@Service
public class NcrServiceImpl extends ServiceImpl<NcrMapper, Ncr> {
    @Autowired
    private NcrMapper ncrMapper;

    public Ncr create(Long userId){
        Ncr ncr = new Ncr();
        ncr.setOwnerId(userId);
        ncr.setState(NcrState.RECORD);
        ncr.setInsertTime(LocalDateTime.now());

        /** 初始化 delegate flag 0-关闭 1-开启 */
        ncr.setDelegateFlag(0);
        ncr.setDelegateState(DelegateState.CLOSE);

        ncrMapper.insert(ncr);

        return ncr;
    }
}
