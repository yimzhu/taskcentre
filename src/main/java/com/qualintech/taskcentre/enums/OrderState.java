package com.qualintech.taskcentre.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实现 IEnum<String> 是为了 Order 的 state 属性在Mybatis中能直接使用此枚举
 * 订单任务 9xx 测试用
 * @author yimzhu
 */
@AllArgsConstructor
@Getter
public enum OrderState {
    UNPAID(900,"待支付"),     //待支付
    PAID(901,"已支付"),       //已支付
//    DELIVERING,  //配送中
//    RECEIVED,    //已签收
//    CLOSED;
;
    @JsonValue
    @EnumValue
    private int code;
    private String name;
}
