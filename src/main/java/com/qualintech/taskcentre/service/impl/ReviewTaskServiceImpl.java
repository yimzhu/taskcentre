package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.FlowTask;
import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.ReviewState;
import com.qualintech.taskcentre.enums.TaskType;
import com.qualintech.taskcentre.mapper.DelegateTaskMapper;
import com.qualintech.taskcentre.mapper.FlowTaskMapper;
import com.qualintech.taskcentre.mapper.ReviewTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author yimzhu
 */
@Slf4j
@Service
public class ReviewTaskServiceImpl extends ServiceImpl<ReviewTaskMapper, ReviewTask> {
    @Autowired
    private FlowTaskMapper flowTaskMapper;

    @Autowired
    private DelegateTaskMapper delegateTaskMapper;

    @Autowired
    private ReviewTaskMapper reviewTaskMapper;

    /**
     * 创建审核任务
     * @param ownerIds 审核人列表
     * @param taskId 上一级任务ID
     * @param taskType 上一级任务类型，0-flow task, 1-delegate task
     * @param taskSate 上一级任务状态
     * @return
     */
    public boolean create(List<Long> ownerIds, Integer taskId, Integer taskType, Integer taskSate){
        int count;

        if(taskType.equals(TaskType.FLOW.getCode())) {
            //设置主任务中对应任务的审核状态为0-开启，1-关闭
            FlowTask flowTask = new FlowTask();
            flowTask.setReviewFlag(1);
            flowTask.setReviewState(ReviewState.OPEN);

            UpdateWrapper<FlowTask> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", taskId);
            count = flowTaskMapper.update(flowTask, updateWrapper);
            log.info("初始化流程任务[" + taskId + "]的审核状态，更新记录数：" + count);
            assert count==1?true:false;
        }
        else if(taskType.equals(TaskType.DELEGATE.getCode())){
            //设置主任务中对应任务的审核状态为0-开启，1-关闭
            DelegateTask delegateTask = new DelegateTask();
            delegateTask.setReviewFlag(1);
            delegateTask.setReviewState(ReviewState.OPEN);

            UpdateWrapper<DelegateTask> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", taskId);
            count= delegateTaskMapper.update(delegateTask, updateWrapper);
            log.info("初始化委托任务[" + taskId + "]的审核状态：" + count);
            assert count==1?true:false;
        }
        else{
            log.error("任务类型不合法！");
        }

        ReviewTask reviewTask = new ReviewTask();
        reviewTask.setOutTaskId(taskId);
        reviewTask.setOutTaskState(taskSate);
        for(Long ownerId:ownerIds){
            reviewTask.setOwnerId(ownerId);
            count = reviewTaskMapper.insert(reviewTask);
            log.info("插入主任务[" + taskId + "]的审核任务，审核人[" + ownerId + "]，状态：" + count + ", id:[" + reviewTask.getId() + "]");
            assert count==1?true:false;
        }
        return true;
    }

    /**
     *  保存流程任务下的审核结果
     * @param ownerId 审核人
     * @param taskId 任务ID
     * @param taskSate 任务状态
     */
    public boolean saveReviewForFlow(Long ownerId, Integer taskId, Module module, Integer taskSate, ReviewState result){
        int count;
        ReviewTask reviewTask = new ReviewTask();
        reviewTask.setResult(result);

        UpdateWrapper<ReviewTask> reviewTaskUpdateWrapper = new UpdateWrapper<>();
        reviewTaskUpdateWrapper.eq("owner_id", ownerId);
        reviewTaskUpdateWrapper.eq("out_task_id", taskId);
        reviewTaskUpdateWrapper.eq("out_task_state", taskSate);
        reviewTaskUpdateWrapper.eq("is_active", 1);

        count = reviewTaskMapper.update(reviewTask, reviewTaskUpdateWrapper);
        log.info("任务ID【" + taskId + "】, 审核人【" + ownerId + "】，审核结果【" + result.getName() + "】，记录保存成功数量【" + count + "】");

        //检查审核是否全部通过
        QueryWrapper<ReviewTask> reviewTaskQueryWrapper = new QueryWrapper<>();
        reviewTaskQueryWrapper.eq("owner_id", ownerId);
        reviewTaskQueryWrapper.eq("out_task_id", taskId);
        reviewTaskQueryWrapper.ne("out_task_state", ReviewState.REVIEW_PASS);
        reviewTaskUpdateWrapper.eq("is_active", 1);
        count = reviewTaskMapper.selectCount(reviewTaskQueryWrapper);
        log.info("任务ID【" + taskId + "】审核未到通过的数量为【" + count + "】");

        if(count!=0){
            return true;
        }

        FlowTask flowTask = new FlowTask();
        flowTask.setReviewState(ReviewState.CLOSE);
        QueryWrapper<FlowTask> materialQueryWrapper = new QueryWrapper<>();
        materialQueryWrapper.eq("module", module);
        materialQueryWrapper.eq("id", taskId);
        count = flowTaskMapper.update(flowTask, materialQueryWrapper);
        log.info("任务ID【" + taskId + "】, 更新流程任务审核为成功【" + count + "】");
        assert count==1?true:false;
        return true;
    }

    /**
     *  保存委托任务下的审核结果
     * @param ownerId 审核人
     * @param taskId 任务ID
     * @param taskSate 任务状态
     */
    public boolean saveReviewForDelegate(Long ownerId, Integer taskId, Integer taskSate, ReviewState result){
        int count;
        ReviewTask reviewTask = new ReviewTask();
        reviewTask.setResult(result);

        UpdateWrapper<ReviewTask> reviewTaskUpdateWrapper = new UpdateWrapper<>();
        reviewTaskUpdateWrapper.eq("owner_id", ownerId);
        reviewTaskUpdateWrapper.eq("out_task_id", taskId);
        reviewTaskUpdateWrapper.eq("out_task_state", taskSate);
        reviewTaskUpdateWrapper.eq("is_active", 1);
        count = reviewTaskMapper.update(reviewTask, reviewTaskUpdateWrapper);
        log.info("任务ID【" + taskId + "】, 审核人【" + ownerId + "】，审核结果【" + result.getName() + "】，记录保存成功【" + count + "】");
        assert count==1?true:false;

        //检查审核是否全部结束
        QueryWrapper<ReviewTask> reviewTaskQueryWrapper = new QueryWrapper<>();
        reviewTaskQueryWrapper.eq("owner_id", ownerId);
        reviewTaskQueryWrapper.eq("out_task_id", taskId);
        reviewTaskQueryWrapper.ne("out_task_state", ReviewState.REVIEW_PASS);
        reviewTaskUpdateWrapper.eq("is_active", 1);
        count = reviewTaskMapper.selectCount(reviewTaskQueryWrapper);
        log.info("任务ID【" + taskId + "】审核未到通过的数量为【" + count + "】");

        if(count!=0){
            return true;
        }

        //把委托任务下的审核状态置为关闭，方便流程跳转。
        DelegateTask delegateTask = new DelegateTask();
        delegateTask.setReviewState(ReviewState.CLOSE);
        QueryWrapper<DelegateTask> delegateTaskQueryWrapper = new QueryWrapper<>();
        delegateTaskQueryWrapper.eq("flow_task_id", taskId);
        count = delegateTaskMapper.update(delegateTask, delegateTaskQueryWrapper);
        log.info("任务ID【" + taskId + "】, 更新委托任务审核为成功【" + count + "】");
        assert count==1?true:false;
        return true;
    }
}
