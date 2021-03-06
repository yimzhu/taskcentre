package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.DelegateType;
import com.qualintech.taskcentre.enums.ReviewState;
import com.qualintech.taskcentre.mapper.DelegateTaskMapper;
import com.qualintech.taskcentre.mapper.ReviewTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author yimzhu
 */
@Slf4j
@Service
public class ReviewTaskServiceImpl extends ServiceImpl<ReviewTaskMapper, ReviewTask> {
    @Autowired
    private DelegateTaskMapper delegateTaskMapper;

    @Autowired
    private ReviewTaskMapper reviewTaskMapper;

    private int count;

    /**
     * 创建审核任务
     * @param ownerIds 审核人列表
     * @param taskType 上一级任务类型
     * @param taskId 上一级任务ID
     * @param taskState 上一级任务状态
     * @return
     */
    public boolean create(List<Long> ownerIds, int taskType, long taskId, int taskState, String expectTime){
        //设置主任务中对应任务的审核状态为0-开启，1-关闭
        DelegateTask delegateTask = new DelegateTask();
        delegateTask.setReviewFlag(1);
        delegateTask.setReviewState(ReviewState.OPEN);

        UpdateWrapper<DelegateTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", taskId);
        count= delegateTaskMapper.update(delegateTask, updateWrapper);
        log.info("初始化委托任务[" + taskId + "]的审核状态：" + count);
        assert count==1?true:false;

        ReviewTask reviewTask = new ReviewTask();
        reviewTask.setDelegateTaskId(taskId);
        DelegateType delegateType = DelegateType.getDelegateType(taskType);
        reviewTask.setDelegateType(delegateType);
        DelegateState delegateState = DelegateState.getDelegateState(taskState);
        reviewTask.setDelegateTaskState(delegateState);
        reviewTask.setResult(ReviewState.IN_REVIEW);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(expectTime,df);
        reviewTask.setOverdueTime(ldt);

        for(Long ownerId:ownerIds){
            reviewTask.setOwnerId(ownerId);
            count = reviewTaskMapper.insert(reviewTask);
            log.info("插入主任务[" + taskId + "]的审核任务，审核人[" + ownerId + "]，状态：" + count + ", id:[" + reviewTask.getId() + "]");
            assert count==1?true:false;
        }
        return true;
    }

    /**
     *  保存委托任务下的审核结果
     * @param ownerId 审核人
     * @param taskId 任务ID
     * @param taskState 流程任务状态
     * @param result 审核结果
     */
    public boolean saveReviewForDelegate(long ownerId, int taskId, int taskState, ReviewState result){
        ReviewTask reviewTask = new ReviewTask();
        reviewTask.setResult(result);

        UpdateWrapper<ReviewTask> reviewTaskUpdateWrapper = new UpdateWrapper<>();
        reviewTaskUpdateWrapper.and(i->i.eq("owner_id", ownerId)
                .eq("out_task_id", taskId)
                .eq("out_task_state", taskState)
                .eq("is_active", 1));
        count = reviewTaskMapper.update(reviewTask, reviewTaskUpdateWrapper);
        log.info("任务ID【" + taskId + "】, 审核人【" + ownerId + "】，审核结果【" + result.getName() + "】，记录保存成功【" + count + "】");
        assert count==1?true:false;

        //检查审核是否全部结束
        count = queryReviewCount(taskId, taskState,0);
        if(count!=0){
            log.info("任务ID【" + taskId + "】的审核流未结束，本次结果已保存。");
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

    /**
     * 查询审核状态（未通过，通过）的数量
     * @param taskId
     * @param taskState
     * @param result 0-未通过（失败+待审核） 1-通过
     * @return
     */
    public int queryReviewCount(int taskId, int taskState, Integer result){
        if(result.equals(0)){
            QueryWrapper<ReviewTask> reviewTaskQueryWrapper = new QueryWrapper<>();
            reviewTaskQueryWrapper.and(i->i.eq("out_task_id", taskId)
                    .eq("delegate_task_state", taskState)
                    .ne("result", ReviewState.REVIEW_PASS)
                    .eq("is_active",1));
            count = reviewTaskMapper.selectCount(reviewTaskQueryWrapper);
            log.info("任务ID【" + taskId + "】审核未通过的数量为【" + count + "】");
        }else if(result.equals(1)){
            QueryWrapper<ReviewTask> reviewTaskQueryWrapper = new QueryWrapper<>();
            reviewTaskQueryWrapper.and(i->i.eq("out_task_id", taskId)
                    .eq("delegate_task_state", taskState)
                    .eq("result", ReviewState.REVIEW_PASS)
                    .eq("is_active",1));
            count = reviewTaskMapper.selectCount(reviewTaskQueryWrapper);
            log.info("任务ID【" + taskId + "】审核通过的数量为【" + count + "】");
        }
        return count;
    }

    /**
     * 查询审核状态（未通过，通过）的数量
     * @param taskId
     * @param taskState
     * @param result 0-未通过（失败+待审核） 1-通过
     * @return
     */
    public List<ReviewTask> queryReviewRecords(int taskId, int taskState, Integer result){
        List<ReviewTask> list = null;
        if(result.equals(0)){
            QueryWrapper<ReviewTask> reviewTaskQueryWrapper = new QueryWrapper<>();
            reviewTaskQueryWrapper.and(i->i.eq("out_task_id", taskId)
                    .eq("delegate_task_state", taskState)
                    .ne("result", ReviewState.REVIEW_PASS));
            list = reviewTaskMapper.selectList(reviewTaskQueryWrapper);
        }else if(result.equals(1)){
            QueryWrapper<ReviewTask> reviewTaskQueryWrapper = new QueryWrapper<>();
            reviewTaskQueryWrapper.and(i->i.eq("out_task_id", taskId)
                    .eq("delegate_task_state", taskState)
                    .eq("result", ReviewState.REVIEW_PASS));
            list = reviewTaskMapper.selectList(reviewTaskQueryWrapper);
        }
        return list;
    }
}
