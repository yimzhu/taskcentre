package com.qualintech.taskcentre.statemachine.conditions;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.mapper.ReviewTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * @author yimzhu
 */
@Component
@Slf4j
public class ReviewCheckCondition extends AnonymousCondition<ReviewTask> {

    private final ReviewTaskMapper reviewTaskMapper;

    public ReviewCheckCondition(ReviewTaskMapper reviewTaskMapper) {
        this.reviewTaskMapper = reviewTaskMapper;
    }

    /**
     * 检查审核任务是否已完成
     * @param context
     * @return
     */
    @Override
    public boolean isSatisfied(ReviewTask context) {
        QueryWrapper<ReviewTask> queryWrapper = new QueryWrapper<>(context);
        queryWrapper.ne("result",1);
        reviewTaskMapper.selectOne(queryWrapper);
        return reviewTaskMapper.selectCount(queryWrapper)==0?true:false;
    }
}
