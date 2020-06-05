package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.entity.Order;
import com.qualintech.taskcentre.enums.OrderEvent;
import com.qualintech.taskcentre.enums.OrderState;
import com.qualintech.taskcentre.mapper.OrderMapper;
import com.qualintech.taskcentre.service.IOrderService;
import com.qualintech.taskcentre.statemachine.OrderStateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStateMachineEngine orderStateMachineEngine;


    @GetMapping("/{id}")
    public Order getOrder(@PathVariable long id) {
//        System.out.print(orderService.getById(id).getState().getName());
//        Order order = new Order();
//        order.setId(6L);
//        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(order);
//        Order order1 = orderMapper.selectOne(queryWrapper);
//        System.out.print(order1.getState().getName());

        return orderService.getById(id);
    }

    @PostMapping("")
    public Order createOrder() {
        Order order = new Order();
        order.setState(OrderState.UNPAID);
        orderService.save(order);
        return order;
    }

    @GetMapping("/{id}/paid")
    public Order orderPaid(@PathVariable long id) throws Exception {
        Order order = orderService.getById(id);
        orderStateMachineEngine.fire(order.getState(), OrderEvent.PAY_SUCCESS, order);
        return order;
    }

    @GetMapping("/{id}/refund")
    public Order orderRefund(@PathVariable long id) throws Exception {
        Order order = orderService.getById(id);
        orderStateMachineEngine.fire(order.getState(), OrderEvent.REFUND_SUCCESS, order);
        return order;
    }


}
