package com.qualintech.taskcentre.statemachine.conditions;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.DelegateType;
import com.qualintech.taskcentre.enums.ReviewState;
import com.qualintech.taskcentre.mapper.DelegateTaskMapper;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * 检查委托任务是否全部完成
 * @author yimzhu
 */
@Component
@Slf4j
public class DelegateReviewCheckCondition extends AnonymousCondition<DelegateTask> {

    private final DelegateTaskMapper delegateTaskMapper;

    public DelegateReviewCheckCondition(DelegateTaskMapper delegateTaskMapper) {
        this.delegateTaskMapper = delegateTaskMapper;
    }

    /**
     * 来料审核状态检查
     * @param context
     * @return
     */
    @Override
    public boolean isSatisfied(DelegateTask context) {
        QueryWrapper<DelegateTask> queryWrapper = new QueryWrapper<>(context);
        queryWrapper.eq("id", context.getId());
        queryWrapper.eq("module", context.getDelegateType());
        queryWrapper.eq("flow_task_id", context.getFlowTaskId());
        queryWrapper.ne("review_state", ReviewState.CLOSE);
        queryWrapper.eq("review_flag",1);
        queryWrapper.eq("is_active", context.getIsActive());
        return delegateTaskMapper.selectCount(queryWrapper)==0?true:false;
    }
}
