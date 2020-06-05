package com.qualintech.taskcentre.domain;

import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.entity.Order;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.OrderState;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.service.IMaterialService;
import com.qualintech.taskcentre.service.IOrderService;
import com.qualintech.taskcentre.statemachine.MaterialStateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskService {
    @Autowired
    private IMaterialService materialService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialStateMachineEngine materialStateMachineEngine;

    /**
     * 创建任务
     * @return 任务id
     */
    public Long create(int taskType){
        if(taskType==Module.MATERIAL.getCode()) {
            Material material = new Material();
            material.setState(MaterialState.INIT);
            material.setModule(Module.MATERIAL);
            materialService.save(material);
            return material.getId();
        }
        else if(taskType==Module.NCR.getCode()){
            Order order = new Order();
            order.setState(OrderState.UNPAID);
            order.setModule(Module.NCR);
            orderService.save(order);
            return order.getId();
        }

        return null;
    }
}
