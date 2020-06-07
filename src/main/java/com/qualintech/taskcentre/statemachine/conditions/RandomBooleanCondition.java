package com.qualintech.taskcentre.statemachine.conditions;

import com.qualintech.taskcentre.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.AnonymousCondition;

import java.util.Random;

/**
 * @author yimzhu
 */
@Component
@Slf4j
public class RandomBooleanCondition extends AnonymousCondition<Order> {
    @Override
    public boolean isSatisfied(Order order) {
        boolean res = new Random().nextBoolean();
        log.info("RandomBooleanCondition result: {} ", res);
        return res;
    }
}
