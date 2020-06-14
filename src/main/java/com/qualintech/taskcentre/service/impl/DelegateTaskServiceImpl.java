package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.FlowTask;
import com.qualintech.taskcentre.entity.TransferHistory;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.DelegateType;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.mapper.DelegateTaskMapper;
import com.qualintech.taskcentre.mapper.FlowTaskMapper;
import com.qualintech.taskcentre.service.IFlowTaskService;
import com.qualintech.taskcentre.service.IDelegateTaskService;
import com.qualintech.taskcentre.service.ITransferHistoryTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.instrument.TransformerManager;

import java.time.ZonedDateTime;

/**
 * @author yimzhu
 */
@Slf4j
@Service
public class DelegateTaskServiceImpl extends ServiceImpl<DelegateTaskMapper,DelegateTask> {
    @Autowired
    private DelegateTaskMapper delegateTaskMapper;

    @Autowired
    private FlowTaskMapper flowTaskMapper;

    @Autowired
    private TransferHistory iTransferHistoryTaskService;

    /**
     * 创建委托任务
     * @param ownerId 委托人
     * @param taskId 任务ID
     * @return
     */
    //TODO 委托人已对象方式替换
    public Long create(Long ownerId, Long taskId){
        //设置主任务中对应任务的委托状态为未完成，委托标记已委托
        FlowTask flowTask = new FlowTask();
        flowTask.setDelegateState(DelegateState.CLOSE);
        flowTask.setDelegateFlag(1);

        int sqlResult;

        UpdateWrapper<FlowTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",taskId);
        sqlResult = flowTaskMapper.update(flowTask,updateWrapper);
        log.info("初始化流程任务[" + taskId + "]的审核状态：" + sqlResult);
        assert sqlResult!=0?true:false;

        DelegateTask delegateTask = new DelegateTask();
//        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        delegateTask.setFlowTaskId(taskId);
        delegateTask.setOwnerId(ownerId);
        delegateTask.setState(DelegateState.INIT);
        sqlResult = delegateTaskMapper.insert(delegateTask);
        log.info("委托任务已创建，委托任务ID【"+delegateTask.getId()+"】, 委托人【"+ownerId+"】");
        assert sqlResult!=0?true:false;

        return delegateTask.getId();
    }

    /**
     *  保存委托结果
     * @param ownerId 审核人
     * @param flowTaskId 任务ID
     * @param delegateState 任务状态
     */
    public boolean save(Long ownerId, Long delegateTaskId, Long flowTaskId, DelegateType type, DelegateState delegateState){
        int result;
        //更新委托任务的状态
        UpdateWrapper<DelegateTask> delegateTaskUpdateWrapper = new UpdateWrapper<>();
        delegateTaskUpdateWrapper.eq("is_active",1);
        delegateTaskUpdateWrapper.eq("flow_task_id", flowTaskId);
        delegateTaskUpdateWrapper.eq("owner_id", ownerId);
        result = delegateTaskMapper.update(DelegateTask.builder().state(delegateState).build(), delegateTaskUpdateWrapper);
        assert result!=0?true:false;

        //查询是否还有委托未到终态的委托任务
        QueryWrapper<DelegateTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_active",1);
        queryWrapper.eq("id", delegateTaskId);
        queryWrapper.eq("flow_task_id", flowTaskId);
        queryWrapper.eq("module", type);
        queryWrapper.or(i->i.eq("state", DelegateState.RECALLED)
                .eq("state", DelegateState.DONE)
                .eq("state", DelegateState.AUDIT_PASS));
        int count = delegateTaskMapper.selectCount(queryWrapper);

        //如没有则更新FlowTask的委托状态, 给状态机做状态跳转判断用
        if(count==0){
            UpdateWrapper<FlowTask> flowTaskUpdateWrapper = new UpdateWrapper<>();
            flowTaskUpdateWrapper.eq("is_active",1);
            flowTaskUpdateWrapper.eq("id", flowTaskId);
            flowTaskMapper.update(FlowTask.builder().delegateState(DelegateState.CLOSE).build(),flowTaskUpdateWrapper);
        }
        return true;
    }

    /**
     * 转交任务
     * @param oldOwnerId 现执行人
     * @param newOwnerId 目标执行人
     * @param taskId 任务ID
     * @param taskState 任务
     * @param module
     * @return
     */
    public boolean transfer(Long oldOwnerId, Long newOwnerId, Long taskId, Integer taskState, Module module){
        Boolean sqlResult;
        //保存当前执行人到历史表
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setTaskId(taskId);
        transferHistory.setModule(module);
        transferHistory.setOldOwnerId(oldOwnerId);
        transferHistory.setNewOwnerId(newOwnerId);
        transferHistory.setTaskState(taskState);
        sqlResult = iTransferHistoryTaskService.save(transferHistory);
        log.info("保存执行人变更记录，执行成功：" + sqlResult);
        assert sqlResult;

        //变更当前委托表中执行人
        UpdateWrapper<DelegateTask> delegateTaskUpdateWrapper = new UpdateWrapper<>();
        delegateTaskUpdateWrapper.eq("is_active",1);
        delegateTaskUpdateWrapper.eq("flow_task_id", taskId);
        delegateTaskUpdateWrapper.eq("owner_id", oldOwnerId);
        sqlResult = iDelegateTaskService.update(DelegateTask.builder().ownerId(newOwnerId).build(), delegateTaskUpdateWrapper);
        log.info("更新当前委托表中执行人：由【" + oldOwnerId + "】到【" + newOwnerId + "】，" + "执行结果成功:" + sqlResult);
        assert sqlResult;
        return true;
    }
}
