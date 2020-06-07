package com.qualintech.taskcentre.statemachine.conditions;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.DelegateState;
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
public class DelegateReviewCheckCondition extends AnonymousCondition<Material> {

    private final MaterialMapper materialMapper;

    public DelegateReviewCheckCondition(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    /**
     * 来料审核状态检查
     * @param context
     * @return
     */
    @Override
    public boolean isSatisfied(Material context) {
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>(context);
        queryWrapper.ne("delegate_state", DelegateState.CLOSE);
        queryWrapper.eq("delegate_flag",1);
        materialMapper.selectOne(queryWrapper);
        return materialMapper.selectCount(queryWrapper)==0?true:false;
    }
}
