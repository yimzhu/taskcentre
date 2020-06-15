package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.Ncr;
import com.qualintech.taskcentre.enums.NcrEvent;
import com.qualintech.taskcentre.enums.NcrState;
import com.qualintech.taskcentre.statemachine.actions.NcrNodePassAction;
import com.qualintech.taskcentre.statemachine.conditions.NcrTransitionCondition;
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


@Service
public class NcrStateMachineEngine implements ApplicationContextAware {
    private StateMachineBuilder<NcrStateMachine, NcrState, NcrEvent, Ncr> stateMachineBuilder;
    private ApplicationContext applicationContext;

    public NcrStateMachineEngine() {
        stateMachineBuilder = StateMachineBuilderFactory.create(NcrStateMachine.class, NcrState.class, NcrEvent.class, Ncr.class, ApplicationContext.class);
    }

    /**
     * 配置 stateMachineBuilder
     * 使用这种方式是为了统一管理 action 与 condition 注解的方式会让人感到不安
     */
    protected void configBuilder() {
        /** 问题信息登记 >> 委托处理问题 >> 委托处理问题 */
        stateMachineBuilder.externalTransition()
                .from(NcrState.RECORD).to(NcrState.RESOLVING).on(NcrEvent.TO_RESOLVING)
                .when(applicationContext.getBean(NcrTransitionCondition.class))
                .perform(applicationContext.getBean(NcrNodePassAction.class));
        /** 委托处理问题 >> 审核结果 >> 问题关闭 */
        stateMachineBuilder.externalTransition()
                .from(NcrState.RESOLVING).to(NcrState.REVIEWING).on(NcrEvent.TO_REVIEW)
                .when(applicationContext.getBean(NcrTransitionCondition.class))
                .perform(applicationContext.getBean(NcrNodePassAction.class));
        /** 问题关闭 >> 关闭 >> 终态 */
        stateMachineBuilder.externalTransition()
                .from(NcrState.REVIEWING).to(NcrState.CLOSED).on(NcrEvent.TO_CLOSE)
                .when(applicationContext.getBean(NcrTransitionCondition.class))
                .perform(applicationContext.getBean(NcrNodePassAction.class));
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
    public void fire(NcrState initialState, NcrEvent trigger, Ncr context) {
        NcrStateMachine stateMachine = createStateMachine(initialState);
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

    protected NcrStateMachine createStateMachine(NcrState initialState) {
        return stateMachineBuilder.newStateMachine(initialState, applicationContext);
    }
}
