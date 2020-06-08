package com.qualintech.taskcentre.controller.contract;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qualintech.taskcentre.enums.Module;
import com.qualintech.taskcentre.enums.ReviewState;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yimzhu
 */
@Data
public class ReviewTaskSaveResultRequest {
    /**
     * ִ����
     */
    @NotNull
    private Long ownerId;
    /**
     * ���������ģ�飨���ϣ�NCR��
     */
    @NotNull
    private Module module;
    /**
     * ��������ID
     */
    @NotNull
    private Integer taskId;
    /**
     *
     */
    @NotNull
    private Integer taskType;
    /**
     *
     */
    @NotNull
    private Integer taskState;
    @EnumValue
    private ReviewState reviewState;

}
