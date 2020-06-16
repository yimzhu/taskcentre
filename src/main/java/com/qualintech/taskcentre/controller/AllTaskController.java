package com.qualintech.taskcentre.controller;

import com.qualintech.taskcentre.entity.FlowDelegateReviewVO;
import com.qualintech.taskcentre.mapper.DelegateTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yimzhu
 */
@RestController
@RequestMapping("all")
public class AllTaskController {

    @Autowired
    private DelegateTaskMapper delegateTaskMapper;

    /**
     * 根据flow task id查询全表
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public List<FlowDelegateReviewVO> getAllTasksById(@PathVariable long id){
        return delegateTaskMapper.getFlowDelegateReviewTask(id);
    }

}
