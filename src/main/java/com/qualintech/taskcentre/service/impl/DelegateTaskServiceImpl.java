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
import com.qualintech.taskcentre.mapper.TransferHistoryMapper;
import com.qualintech.taskcentre.service.IFlowTaskService;
import com.qualintech.taskcentre.service.IDelegateTaskService;
import com.qualintech.taskcentre.service.ITransferHistoryTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.instrument.TransformerManager;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yimzhu
 */
@Slf4j
@Service
public class DelegateTaskServiceImpl extends ServiceImpl<DelegateTaskMapper,DelegateTask> {
    @Autowired
    private FlowTaskServiceImpl flowTaskService;

    @Autowired
    private TransferHistoryServiceImpl transferHistoryService;

    /**
     * 创建委托任务
     * @param ownerId 委托人
     * @param flowTaskId 流程任务ID
     * @param delegateType 委托类型
     * @return
     */
    //TODO 委托人已对象方式替换
    public Long create(Long ownerId, Long flowTaskId, DelegateType delegateType, String expectTime){
        //设置主任务中对应任务的委托状态为未完成，委托标记已委托
        FlowTask flowTask = new FlowTask();
        flowTask.setDelegateState(DelegateState.CLOSE);
        flowTask.setDelegateFlag(1);

        boolean sqlResult;

        UpdateWrapper<FlowTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",flowTaskId);
        sqlResult = flowTaskService.update(flowTask,updateWrapper);
        log.info("初始化流程任务ID[" + flowTaskId + "]的委托状态：" + sqlResult);
        assert sqlResult;

        DelegateTask delegateTask = new DelegateTask();
        delegateTask.setFlowTaskId(flowTaskId);
        delegateTask.setOwnerId(ownerId);
        delegateTask.setDelegateType(delegateType);
        delegateTask.setState(DelegateState.INIT);
        delegateTask.setInsertTime(LocalDateTime.now());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(expectTime,df);
        delegateTask.setOverdueTime(ldt);

        sqlResult = this.save(delegateTask);
        log.info("委托任务已创建, 初始化状态【" + DelegateState.INIT.getName() + "】，委托任务ID【"+ delegateTask.getId() +"】, 委托人【"+ ownerId +"】");
        assert sqlResult;

        return delegateTask.getId();
    }

    /**
     *  保存委托结果
     * @param ownerId 审核人
     * @param flowTaskId 任务ID
     * @param delegateState 任务状态
     */
    public boolean save(Long ownerId, Long delegateTaskId, Long flowTaskId, DelegateType type, DelegateState delegateState){
        boolean result;
        //更新委托任务的状态
        UpdateWrapper<DelegateTask> delegateTaskUpdateWrapper = new UpdateWrapper<>();
        delegateTaskUpdateWrapper.eq("is_active",1);
        delegateTaskUpdateWrapper.eq("flow_task_id", flowTaskId);
        delegateTaskUpdateWrapper.eq("owner_id", ownerId);
        result = this.update(DelegateTask.builder().state(delegateState).build(), delegateTaskUpdateWrapper);
        assert result;

        //查询是否还有委托未到终态的委托任务
        QueryWrapper<DelegateTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_active",1);
        queryWrapper.eq("id", delegateTaskId);
        queryWrapper.eq("flow_task_id", flowTaskId);
        queryWrapper.eq("module", type);
        queryWrapper.or(i->i.eq("state", DelegateState.RECALLED)
                .eq("state", DelegateState.DONE)
                .eq("state", DelegateState.AUDIT_PASS));
        int count = this.count(queryWrapper);

        //如没有则更新FlowTask的委托状态, 给状态机做状态跳转判断用
        if(count==0){
            UpdateWrapper<FlowTask> flowTaskUpdateWrapper = new UpdateWrapper<>();
            flowTaskUpdateWrapper.eq("is_active",1);
            flowTaskUpdateWrapper.eq("id", flowTaskId);
            flowTaskService.update(FlowTask.builder().delegateState(DelegateState.CLOSE).build(),flowTaskUpdateWrapper);
        }
        return true;
    }

    /**
     * 转交任务
     * @param oldOwnerId 现执行人
     * @param newOwnerId 目标执行人
     * @param delegateTaskId 任务ID
     * @param taskState 任务
     * @return
     */
    public boolean transfer(Long oldOwnerId, Long newOwnerId, Long delegateTaskId, Integer taskState){
        boolean sqlResult;

        QueryWrapper<DelegateTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", delegateTaskId);
        DelegateTask delegateTask = this.getOne(queryWrapper);

        //保存当前执行人到历史表
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setDelegateTaskId(delegateTaskId);
        transferHistory.setDelegateType(delegateTask.getDelegateType());
        transferHistory.setOldOwnerId(oldOwnerId);
        transferHistory.setNewOwnerId(newOwnerId);
        transferHistory.setTaskState(taskState);
        sqlResult = transferHistoryService.save(transferHistory);
        log.info("保存执行人变更记录，执行成功：" + sqlResult);
        assert sqlResult;

        //变更当前委托表中执行人
        UpdateWrapper<DelegateTask> delegateTaskUpdateWrapper = new UpdateWrapper<>();
        delegateTaskUpdateWrapper.eq("is_active",1);
        delegateTaskUpdateWrapper.eq("id", delegateTaskId);
        delegateTaskUpdateWrapper.eq("owner_id", oldOwnerId);
        sqlResult = this.update(DelegateTask.builder().ownerId(newOwnerId).build(), delegateTaskUpdateWrapper);
        log.info("更新当前委托表中执行人：由【" + oldOwnerId + "】到【" + newOwnerId + "】，" + "执行结果成功:" + sqlResult);
        assert sqlResult;
        return true;
    }
}
