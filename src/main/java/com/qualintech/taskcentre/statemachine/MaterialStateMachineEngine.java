package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.statemachine.actions.MaterialNodePassAction;
import com.qualintech.taskcentre.statemachine.conditions.MaterialReviewCheckCondition;
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
public class MaterialStateMachineEngine implements ApplicationContextAware {

    private StateMachineBuilder<MaterialStateMachine, MaterialState, MaterialEvent, Material> stateMachineBuilder;

    private ApplicationContext applicationContext;

    public MaterialStateMachineEngine() {
        stateMachineBuilder = StateMachineBuilderFactory.create(MaterialStateMachine.class, MaterialState.class, MaterialEvent.class, Material.class, ApplicationContext.class);
    }

    /**
     * 配置 stateMachineBuilder
     * 使用这种方式是为了统一管理 action 与 condition 注解的方式会让人感到不安
     */
    protected void configBuilder() {
        /** 待发送 >> 派发任务 >> 处理中 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.INIT).to(MaterialState.PROCESSING).on(MaterialEvent.DISPATCH)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 处理中 >> 无审核 >> 已完成 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.PROCESSING).to(MaterialState.DONE).on(MaterialEvent.COMPLETE_WITHOUT_AUDIT)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 处理中 >> 有审核 >> 审核中 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.PROCESSING).to(MaterialState.AUDITING).on(MaterialEvent.COMPLETE_WITH_AUDIT)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 审核中 >> 审核通过 >> 审核通过 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.AUDITING).to(MaterialState.AUDIT_PASS).on(MaterialEvent.PASS_AUDIT)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 审核中 >> 审核驳回 >> 审核驳回 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.AUDITING).to(MaterialState.AUDIT_REJECT).on(MaterialEvent.REJECT_AUDIT)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 审核中 >> 审核通过 >> 审核通过 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.INIT).to(MaterialState.RECALLED).on(MaterialEvent.RECALL)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 待发送 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.INIT).to(MaterialState.RECALLED).on(MaterialEvent.RECALL)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 处理中 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.PROCESSING).to(MaterialState.RECALLED).on(MaterialEvent.RECALL)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 已完成 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.DONE).to(MaterialState.RECALLED).on(MaterialEvent.RECALL)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 审核中 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.AUDITING).to(MaterialState.RECALLED).on(MaterialEvent.RECALL)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 审核通过 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.AUDIT_PASS).to(MaterialState.RECALLED).on(MaterialEvent.RECALL)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 审核驳回 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.AUDIT_PASS).to(MaterialState.RECALLED).on(MaterialEvent.RECALL)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 逾期 >> 召回任务 >> 已召回 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.OVERDUE).to(MaterialState.RECALLED).on(MaterialEvent.RECALL)
                .when(applicationContext.getBean(MaterialReviewCheckCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
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
    public void fire(MaterialState initialState, MaterialEvent trigger, Material context) {
        MaterialStateMachine stateMachine = createStateMachine(initialState);
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

    protected MaterialStateMachine createStateMachine(MaterialState initialState) {
        return stateMachineBuilder.newStateMachine(initialState, applicationContext);
    }
}
