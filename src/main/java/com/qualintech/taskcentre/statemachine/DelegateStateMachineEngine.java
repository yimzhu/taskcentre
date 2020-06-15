package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.DelegateTask;
import com.qualintech.taskcentre.enums.DelegateEvent;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.statemachine.actions.DelegateNodePassAction;
import com.qualintech.taskcentre.statemachine.conditions.DelegateTransitionCondition;
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
public class DelegateStateMachineEngine implements ApplicationContextAware {
    private StateMachineBuilder<DelegateStateMachine, DelegateState, DelegateEvent, DelegateTask> stateMachineBuilder;

    private ApplicationContext applicationContext;

    public DelegateStateMachineEngine() {
        //OrderStateMachineEngine 本身为 一个 service, 即间接实现了 stateMachineBuilder 的单例
        stateMachineBuilder = StateMachineBuilderFactory.create(DelegateStateMachine.class, DelegateState.class, DelegateEvent.class, DelegateTask.class, ApplicationContext.class);
    }

    /**
     * 配置 stateMachineBuilder
     * 使用这种方式是为了统一管理 action 与 condition 注解的方式会让人感到不安
     */
    protected void configBuilder() {
        stateMachineBuilder.externalTransition()
                .from(DelegateState.INIT).to(DelegateState.PROCESSING).on(DelegateEvent.DISPATCH)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
/** 处理中 >> 无审核 >> 已完成 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.PROCESSING).to(DelegateState.DONE).on(DelegateEvent.COMPLETE_WITHOUT_AUDIT)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 处理中 >> 有审核 >> 审核中 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.PROCESSING).to(DelegateState.AUDITING).on(DelegateEvent.COMPLETE_WITH_AUDIT)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 审核中 >> 审核通过 >> 审核通过 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.AUDITING).to(DelegateState.AUDIT_PASS).on(DelegateEvent.PASS_AUDIT)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 审核中 >> 审核驳回 >> 审核驳回 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.AUDITING).to(DelegateState.AUDIT_REJECT).on(DelegateEvent.REJECT_AUDIT)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 审核中 >> 审核通过 >> 审核通过 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.INIT).to(DelegateState.RECALLED).on(DelegateEvent.RECALL)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 待发送 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.INIT).to(DelegateState.RECALLED).on(DelegateEvent.RECALL)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 处理中 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.PROCESSING).to(DelegateState.RECALLED).on(DelegateEvent.RECALL)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 已完成 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.DONE).to(DelegateState.RECALLED).on(DelegateEvent.RECALL)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 审核中 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.AUDITING).to(DelegateState.RECALLED).on(DelegateEvent.RECALL)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 审核通过 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.AUDIT_PASS).to(DelegateState.RECALLED).on(DelegateEvent.RECALL)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 审核驳回 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.AUDIT_PASS).to(DelegateState.RECALLED).on(DelegateEvent.RECALL)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 已完成 >> 重开任务 >> 处理中 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.DONE).to(DelegateState.PROCESSING).on(DelegateEvent.RESET)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
        /** 审核通过 >> 重开任务 >> 处理中 */
        stateMachineBuilder.externalTransition()
                .from(DelegateState.AUDIT_PASS).to(DelegateState.PROCESSING).on(DelegateEvent.RESET)
                .when(applicationContext.getBean(DelegateTransitionCondition.class))
                .perform(applicationContext.getBean(DelegateNodePassAction.class));
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
    public void fire(DelegateState initialState, DelegateEvent trigger, DelegateTask context) {
        DelegateStateMachine stateMachine = createStateMachine(initialState);
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

    protected DelegateStateMachine createStateMachine(DelegateState initialState) {
        return stateMachineBuilder.newStateMachine(initialState, applicationContext);
    }
}
