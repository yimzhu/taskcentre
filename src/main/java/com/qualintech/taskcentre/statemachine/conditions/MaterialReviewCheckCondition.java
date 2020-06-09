package com.qualintech.taskcentre.statemachine.conditions;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.ReviewState;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

/**
 * 检查审核是否全部通过
 * @author yimzhu
 */
@Component
@Slf4j
public class MaterialReviewCheckCondition extends AnonymousCondition<Material> {

    private final MaterialMapper materialMapper;

    public MaterialReviewCheckCondition(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    /**
     * 来料审核状态检查
     * @param context
     * @return
     */
    @Override
    public boolean isSatisfied(Material context) {
        log.info("来料任务【" + context.getId() + "】进行审核及委托任务完成条件校验...");
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", context.getId());
        queryWrapper.and(i->i.ne("review_state", ReviewState.CLOSE).eq("review_flag",1));
        queryWrapper.or(i->i.ne("delegate_state", DelegateState.CLOSE).eq("delegate_flag",1));
        int count = materialMapper.selectCount(queryWrapper);
        boolean result = count==0?true:false;
        log.info("来料任务【" + context.getId() + "】审核及委托任务完成条件校验结果："+result);
        return result;
    }
}
