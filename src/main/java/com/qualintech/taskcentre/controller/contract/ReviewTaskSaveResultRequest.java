package com.qualintech.taskcentre.controller.contract;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.ReviewState;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yimzhu
 */
@Data
public class ReviewTaskSaveResultRequest {
    /**
     * ִ����
     */
    private Long ownerId;
    /**
     * ���������ģ�飨���ϣ�NCR��
     */

    private Module module;
    /**
     * ��������ID
     */
    private Integer taskId;
    /**
     *
     */
    private Integer taskType;
    /**
     *
     */
    private Integer taskSate;
    @EnumValue
    private ReviewState reviewState;

}
