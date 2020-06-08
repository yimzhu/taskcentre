package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.MaterialFlowTaskCreateRequest;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.service.IMaterialService;
import com.qualintech.taskcentre.service.impl.MaterialServiceImpl;
import com.qualintech.taskcentre.statemachine.MaterialStateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("material")
public class MaterialController {

    @Autowired
    private MaterialServiceImpl materialService;

    @Autowired
    private MaterialStateMachineEngine materialStateMachineEngine;


    @GetMapping("/{id}")
    public Material get(@PathVariable long id) {
        return materialService.getById(id);
    }

    @PostMapping("")
    public Material create(@RequestBody MaterialFlowTaskCreateRequest request) {
        Material material = new Material();
        material.setOwnerId(request.getOwnerId());
        material.setState(MaterialState.INIT);
        materialService.save(material);
        return material;
    }

    @GetMapping("/{id}/dispatch")
    public Material dispatch(@PathVariable long id) throws Exception {
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
