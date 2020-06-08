package com.qualintech.taskcentre.statemachine.actions;

import com.qualintech.taskcentre.entity.Order;
import com.qualintech.taskcentre.enums.OrderEvent;
import com.qualintech.taskcentre.enums.OrderState;
import com.qualintech.taskcentre.service.impl.OrderServiceImpl;
import com.qualintech.taskcentre.statemachine.OrderStateMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousAction;

@Slf4j
@Component
public class ReviewPassAction extends AnonymousAction<OrderStateMachine, OrderState, OrderEvent, Order> {

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public void execute(OrderState orderState, OrderState s1, OrderEvent event, Order order, OrderStateMachine orderStateMachine) {
        Order nextOrder = orderService.getById(order.getId()+1);
        log.info("next order info: {}", nextOrder);
        log.info("executed by class: {}", ReviewPassAction.class.toString());
    }
}
