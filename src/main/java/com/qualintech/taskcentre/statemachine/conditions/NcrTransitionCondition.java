package com.qualintech.taskcentre.statemachine.conditions;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.entity.Ncr;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.mapper.NcrMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * @author yimzhu
 */
@Component
@Slf4j
public class NcrTransitionCondition extends AnonymousCondition<Ncr> {

    private final NcrMapper ncrMapper;

    public NcrTransitionCondition(NcrMapper ncrMapper) {
        this.ncrMapper = ncrMapper;
    }

    /**
     * 检查审核任务是否已完成
     * @param context
     * @return
     */
    @Override
    public boolean isSatisfied(Ncr context) {
        QueryWrapper<Ncr> queryWrapper = new QueryWrapper<>(context);
        queryWrapper.eq("delegate_flag",DelegateState.CLOSE);
        queryWrapper.eq("delegate_flag",1);
        return ncrMapper.selectCount(queryWrapper)==0?true:false;
    }
}
