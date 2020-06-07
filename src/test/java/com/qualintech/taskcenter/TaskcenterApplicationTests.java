package com.qualintech.taskcenter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qualintech.taskcentre.StatemachineDemoApplication;
import com.qualintech.taskcentre.entity.FlowTask;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.mapper.FlowTaskMapper;
import com.qualintech.taskcentre.service.IFlowTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@SpringBootTest(classes = StatemachineDemoApplication.class)
class TaskcenterApplicationTests {
    @Autowired
    private FlowTaskMapper flowTaskMapper;

    @Test
    void contextLoads() {

    }

    @Test
    public void testAX(){
        QueryWrapper<FlowTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 16);
        int b = flowTaskMapper.selectCount(queryWrapper);
        System.out.println(b);
//        Material m = new Material();
//        System.out.println(m.getModule().getCode());
    }

}
