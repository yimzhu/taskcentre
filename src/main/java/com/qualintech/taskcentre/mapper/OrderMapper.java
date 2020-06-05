package com.qualintech.taskcentre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qualintech.taskcentre.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {

}