package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.ReviewTask;
import com.qualintech.taskcentre.enums.ReviewEvent;
import com.qualintech.taskcentre.enums.ReviewState;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;


/**
 * @author yimzhu
 */
@Service
public class ReviewTaskStateMachineEngine implements ApplicationContextAware {
    private StateMachineBuilder<ReviewTaskStateMachine, ReviewState, ReviewEvent, ReviewTask> stateMachineBuilder;

    private ApplicationContext applicationContext;

    public ReviewTaskStateMachineEngine() {
        //OrderStateMachineEngine 本身为 一个 service, 即间接实现了 stateMachineBuilder 的单例
        stateMachineBuilder = StateMachineBuilderFactory.create(ReviewTaskStateMachine.class, ReviewState.class, ReviewEvent.class, ReviewTask.class, ApplicationContext.class);
    }

    /**
     * 配置 stateMachineBuilder
     * 使用这种方式是为了统一管理 action 与 condition 注解的方式会让人感到不安
     */
    protected void configBuilder() {
        stateMachineBuilder.externalTransition()
                .from(ReviewState.IN_REVIEW).to(ReviewState.REVIEW_PASS).on(ReviewEvent.PASS);
//                .when(applicationContext.getBean(ReviewCheckCondition.class));

        stateMachineBuilder.externalTransition()
                .from(ReviewState.IN_REVIEW).to(ReviewState.REVIEW_FAIL).on(ReviewEvent.REJECT);
//                .when(applicationContext.getBean(ReviewCheckCondition.class));
//                .perform(orderPaySuccessAction);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        // 实现了 ApplicationContextAware 接口, 可以获取 spring ApplicationContext 上下文
        // 获取到上下文之后, 进行 builder 的配置
        this.configBuilder();
    }

    /**
     * 代理stateMachine 的 fire 使其可以增加 事务 以及 锁的特性
     * 可参考 https://segmentfault.com/a/1190000009906469 文章中 "分布式锁+事务" 章节
     * @param initialState
     * @param trigger
     * @param context
     */
    public void fire(ReviewState initialState, ReviewEvent trigger, ReviewTask context) {
        ReviewTaskStateMachine stateMachine = createStateMachine(initialState);
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            stateMachine.fire(trigger, context);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    protected ReviewTaskStateMachine createStateMachine(ReviewState initialState) {
        return stateMachineBuilder.newStateMachine(initialState, applicationContext);
    }
}
