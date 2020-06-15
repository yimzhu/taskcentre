package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.entity.TransferHistory;
import com.qualintech.taskcentre.enums.ReviewState;
import com.qualintech.taskcentre.mapper.DelegateTaskMapper;
import com.qualintech.taskcentre.mapper.ReviewTaskMapper;
import com.qualintech.taskcentre.mapper.TransferHistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yimzhu
 */
@Slf4j
@Service
public class TransferHistoryServiceImpl extends ServiceImpl<TransferHistoryMapper, TransferHistory> {

}
