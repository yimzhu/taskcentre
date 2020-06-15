package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.controller.contract.FlowTaskCreateRequest;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.entity.Ncr;
import com.qualintech.taskcentre.enums.*;
import com.qualintech.taskcentre.service.impl.MaterialServiceImpl;
import com.qualintech.taskcentre.service.impl.NcrServiceImpl;
import com.qualintech.taskcentre.statemachine.MaterialStateMachineEngine;
import com.qualintech.taskcentre.statemachine.NcrStateMachineEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yimzhu
 */
@RestController
@RequestMapping("flow")
public class FlowController {
    @Autowired
    private MaterialServiceImpl materialService;
    @Autowired
    private NcrServiceImpl ncrService;
    @Autowired
    private MaterialStateMachineEngine materialStateMachineEngine;
    @Autowired
    private NcrStateMachineEngine ncrStateMachineEngine;

    @GetMapping("{flow}/{id}")
    public Object get(@PathVariable long flow, @PathVariable long id) {
        if(flow== Module.MATERIAL.getCode()){
            return materialService.getById(id);
        }else if(flow== Module.NCR.getCode()){
            return ncrService.getById(id);
        }else{
            return null;
        }
    }

    @PostMapping("")
    public Object create(@RequestBody FlowTaskCreateRequest request) {
        if(request.getModule()== Module.MATERIAL){
            return materialService.create(request.getOwnerId());
        }else if(request.getModule()== Module.NCR){
            return ncrService.create(request.getOwnerId());
        }else{
            return null;
        }
    }

    @GetMapping("{module}/{id}/{event}")
    public Object dispatch(@PathVariable String module, @PathVariable long id, @PathVariable long event) {
        if(module==Module.MATERIAL.getName()){
            Material material = materialService.getById(id);
            MaterialEvent materialEvent = MaterialEvent.valueOf(String.valueOf(event));
            materialStateMachineEngine.fire(material.getState(), materialEvent, material);
            return material;
        }else if(module==Module.NCR.getName()){
            Ncr ncr = ncrService.getById(id);
            NcrEvent ncrEvent = NcrEvent.valueOf(String.valueOf(event));
            ncrStateMachineEngine.fire(ncr.getState(), ncrEvent, ncr);
            return ncr;
        }else{
            return null;
        }
    }

//    @GetMapping("/{id}/refund")
//    public Material materialRefund(@PathVariable long id) throws Exception {
//        Material material = materialService.getById(id);
//        materialStateMachineEngine.fire(material.getState(), OrderEvent.DELIVER, material);
//        return order;
//    }


}
