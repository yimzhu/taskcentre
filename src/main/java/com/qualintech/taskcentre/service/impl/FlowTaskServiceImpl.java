package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.FlowTask;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.entity.Ncr;
import com.qualintech.taskcentre.entity.Order;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.NcrState;
import com.qualintech.taskcentre.enums.OrderState;
import com.qualintech.taskcentre.mapper.FlowTaskMapper;
import com.qualintech.taskcentre.service.INcrService;
import com.qualintech.taskcentre.statemachine.MaterialStateMachineEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yimzhu
 */
@Slf4j
@Service
public class FlowTaskServiceImpl extends ServiceImpl<FlowTaskMapper, FlowTask> {
    @Autowired
    private MaterialServiceImpl materialService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private NcrServiceImpl ncrService;


    /**
     * 创建流程任务
     * @return task id
     */
    public Long create(Module module){
        //TODO 工厂类包装？
        if(module.equals(Module.MATERIAL)) {
            Material material = new Material();
            material.setState(MaterialState.BASIC_INFO);
            material.setModule(Module.MATERIAL);
            materialService.save(material);
            log.info("流程任务已创建，任务类型【" + module.getName() + "】，任务【" + material.getId() + "】");
            return material.getId();
        }
        else if(module.equals(Module.NCR)){
            Ncr ncr = new Ncr();
            ncr.setState(NcrState.RECORD);
            ncr.setModule(Module.NCR);
            ncrService.save(ncr);
            log.info("流程任务已创建，任务类型【" + module.getName() + "】，任务【" + ncr.getId() + "】");
            return ncr.getId();
        }
        else if(module.equals(Module.Order)){
            Order order = new Order();
            order.setState(OrderState.UNPAID);
            order.setModule(Module.Order);
            orderService.save(order);
            log.info("流程任务已创建，任务类型【" + module.getName() + "】，任务【" + order.getId() + "】");
            return order.getId();
        }
        return null;
    }
}
