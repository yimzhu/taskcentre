package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.FlowTask;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.entity.Ncr;
import com.qualintech.taskcentre.entity.Order;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.OrderState;
import com.qualintech.taskcentre.mapper.FlowTaskMapper;
import com.qualintech.taskcentre.mapper.NcrMapper;
import com.qualintech.taskcentre.service.INcrService;
import com.qualintech.taskcentre.statemachine.MaterialStateMachineEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yimzhu
 */
@Slf4j
public class NcrServiceImpl extends ServiceImpl<NcrMapper, Ncr> {

}
