package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.Order;
import com.qualintech.taskcentre.enums.OrderEvent;
import com.qualintech.taskcentre.enums.OrderState;
import com.qualintech.taskcentre.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

@Slf4j
public class OrderStateMachine extends AbstractStateMachine<OrderStateMachine, OrderState, OrderEvent, Order> {

    private ApplicationContext applicationContext;

    public OrderStateMachine(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void afterTransitionCompleted(OrderState fromState, OrderState toState, OrderEvent event, Order order) {
            order.setState(toState);
            IOrderService orderService = applicationContext.getBean(IOrderService.class);
            orderService.updateById(order);
    }
}
