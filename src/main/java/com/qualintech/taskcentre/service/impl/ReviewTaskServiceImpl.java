package com.qualintech.taskcentre.service.impl;

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
        for(Long ownerId:ownerIds){
            reviewTask.setOwnerId(ownerId);
            reviewTask.setTaskId(taskId);
            reviewTask.setTaskState(taskSate);
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
    public boolean saveReviewResForFlowTask(Long ownerId, Integer taskId, Module module, Integer taskSate, ReviewState result){
        int sqlResult;

        ReviewTask reviewTask = new ReviewTask();
        reviewTask.setResult(result);

        UpdateWrapper<ReviewTask> reviewTaskUpdateWrapper = new UpdateWrapper<>();
        reviewTaskUpdateWrapper.eq("owner_id", ownerId);
        reviewTaskUpdateWrapper.eq("task_id", taskId);
        reviewTaskUpdateWrapper.eq("task_state", taskSate);
        reviewTaskUpdateWrapper.eq("is_active", 1);

        sqlResult = reviewTaskMapper.update(reviewTask, reviewTaskUpdateWrapper);
        log.info("任务ID【" + taskId + "】, 审核人【" + ownerId + "】，审核结果【" + result.getName() + "】，记录保存成功数量【" + sqlResult + "】");

        //检查审

        QueryWrapper<ReviewTask> reviewTaskQueryWrapper = new QueryWrapper<>();
        reviewTaskQueryWrapper.eq("owner_id", ownerId);
        reviewTaskQueryWrapper.eq("task_id", taskId);
        reviewTaskQueryWrapper.eq("task_state", ReviewState.REVIEW_FAIL);
        reviewTaskQueryWrapper.eq("task_state", ReviewState.IN_REVIEW);
//        int count = iReviewTaskService.count(reviewTaskQueryWrapper);
//        log.info("任务ID【" + taskId + "】审核未到通过的数量为【" + count + "】");

//        if(count!=0){
//            return true;
//        }


        FlowTask flowTask = new FlowTask();
        flowTask.setReviewState(ReviewState.CLOSE);
        QueryWrapper<FlowTask> flowTaskQueryWrapper = new QueryWrapper<>();
        flowTaskQueryWrapper.eq("module", module);
        flowTaskQueryWrapper.eq("id", taskId);
//        sqlResult = iFlowTaskService.update(flowTask, flowTaskQueryWrapper);
//        log.info("任务ID【" + taskId + "】, 更新流程任务审核为成功【" + sqlResult + "】");
//        assert sqlResult;

        return true;
    }

    /**
     *  保存委托任务下的审核结果
     * @param ownerId 审核人
     * @param taskId 任务ID
     * @param taskSate 任务状态
     */
//    public boolean saveReviewResForDelegateTask(Long ownerId, Integer taskId,  Integer taskSate, ReviewState result){
//        Boolean sqlResult;
//
//        ReviewTask reviewTask = new ReviewTask();
//        reviewTask.setResult(result);
//
//        UpdateWrapper<ReviewTask> reviewTaskUpdateWrapper = new UpdateWrapper<>();
//        reviewTaskUpdateWrapper.eq("owner_id", ownerId);
//        reviewTaskUpdateWrapper.eq("task_id", taskId);
//        reviewTaskUpdateWrapper.eq("task_state", taskSate);
//        reviewTaskUpdateWrapper.eq("is_active", 1);
//
//        sqlResult = iReviewTaskService.update(reviewTask, reviewTaskUpdateWrapper);
//        log.info("任务ID【" + taskId + "】, 审核人【" + ownerId + "】，审核结果【" + result.getName() + "】，记录保存成功【" + sqlResult + "】");
//
//        assert sqlResult;
//        //检查审核是否全部结束
//
//        QueryWrapper<ReviewTask> reviewTaskQueryWrapper = new QueryWrapper<>();
//        reviewTaskQueryWrapper.eq("owner_id", ownerId);
//        reviewTaskQueryWrapper.eq("task_id", taskId);
//        reviewTaskQueryWrapper.eq("task_state", ReviewState.REVIEW_FAIL);
//        reviewTaskQueryWrapper.eq("task_state", ReviewState.IN_REVIEW);
//        int count = iReviewTaskService.count(reviewTaskQueryWrapper);
//        log.info("任务ID【" + taskId + "】审核未到通过的数量为【" + count + "】");
//
//        if(count!=0){
//            return true;
//        }
//
//        DelegateTask delegateTask = new DelegateTask();
//        delegateTask.setReviewState(ReviewState.CLOSE);
//        QueryWrapper<DelegateTask> delegateTaskQueryWrapper = new QueryWrapper<>();
//        delegateTaskQueryWrapper.eq("flow_task_id", taskId);
//        sqlResult = iDelegateTaskService.update(delegateTask, delegateTaskQueryWrapper);
//        log.info("任务ID【" + taskId + "】, 更新委托任务审核为成功【" + sqlResult + "】");
//        assert sqlResult;
//
//        return true;
//    }
}
