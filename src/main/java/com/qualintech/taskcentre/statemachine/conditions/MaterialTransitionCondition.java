package com.qualintech.taskcentre.statemachine.conditions;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.FlowTask;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.mapper.DelegateTaskMapper;
import com.qualintech.taskcentre.mapper.FlowTaskMapper;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.mapper.ReviewTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * @author yimzhu
 */
@Component
@Slf4j
public class MaterialTransitionCondition extends AnonymousCondition<Material> {
    private final MaterialMapper materialMapper;
    public MaterialTransitionCondition(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    /**
     * 检查审核任务是否已完成
     * @param context
     * @return
     */
    @Override
    public boolean isSatisfied(Material context) {
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>(context);
        queryWrapper.eq("delegate_flag",DelegateState.CLOSE);
        queryWrapper.eq("delegate_flag",1);
        return materialMapper.selectCount(queryWrapper)==0?true:false;
    }
}
