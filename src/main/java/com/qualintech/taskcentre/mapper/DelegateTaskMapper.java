package com.qualintech.taskcentre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.FlowDelegateReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yimzhu
 */
@Mapper
@Repository
public interface DelegateTaskMapper extends BaseMapper<DelegateTask> {
    @Select("SELECT f.id,f.state,f.module,f.owner_id,f.insert_time,f.complete_time,f.delegate_state,f.delegate_flag,d.* FROM delegate_task d,flow_task f,review_task r WHERE f.id=d.flow_task_id and r.out_task_id = f.id and f.id=#{id}")
    List<FlowDelegateReviewVO> getFlowDelegateReviewTask(@Param("id")long id);
}