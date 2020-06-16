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
    @Select("SELECT * FROM (flow_task f left join delegate_task d on d.flow_task_id = f.id) left join review_task r on d.id = r.delegate_task_id where f.id=#{id}")
    List<FlowDelegateReviewVO> getFlowDelegateReviewTask(@Param("id")long id);
}