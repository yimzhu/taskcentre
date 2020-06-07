package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.MaterialFlowTaskCreateRequest;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.service.IMaterialService;
import com.qualintech.taskcentre.statemachine.MaterialStateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("material")
public class MaterialController {

    @Autowired
    private IMaterialService materialService;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialStateMachineEngine materialStateMachineEngine;


    @GetMapping("/{id}")
    public Material getMaterial(@PathVariable long id) {
//        System.out.print(orderService.getById(id).getState().getName());
//        Order order = new Order();
//        order.setId(6L);
//        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(order);
//        Order order1 = orderMapper.selectOne(queryWrapper);
//        System.out.print(order1.getState().getName());

        return materialService.getById(id);
    }

    @PostMapping("")
    public Material createMaterial(@RequestBody MaterialFlowTaskCreateRequest request) {
        Material material = new Material();
        material.setOwnerId(request.getOwnerId());
        material.setState(MaterialState.INIT);
        materialService.save(material);
        return material;
    }

    @GetMapping("/{id}/dispatch")
    public Material orderPaid(@PathVariable long id) throws Exception {
        Material material = materialService.getById(id);
        materialStateMachineEngine.fire(material.getState(), MaterialEvent.DISPATCH, material);
        return material;
    }

//    @GetMapping("/{id}/refund")
//    public Material materialRefund(@PathVariable long id) throws Exception {
//        Material material = materialService.getById(id);
//        materialStateMachineEngine.fire(material.getState(), OrderEvent.DELIVER, material);
//        return order;
//    }


}
