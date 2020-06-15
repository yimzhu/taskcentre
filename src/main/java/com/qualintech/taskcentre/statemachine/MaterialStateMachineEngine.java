package com.qualintech.taskcentre.statemachine;

import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.MaterialEvent;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.statemachine.actions.MaterialNodePassAction;
import com.qualintech.taskcentre.statemachine.conditions.MaterialTransitionCondition;
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
        /** 基本信息 >> 检验 >> 检验记录 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.BASIC_INFO).to(MaterialState.INSPECTION_RECORDS).on(MaterialEvent.TO_INSPECTION)
                .when(applicationContext.getBean(MaterialTransitionCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 检验记录 >> 判定 >> 结论判定 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.INSPECTION_RECORDS).to(MaterialState.CONCLUSION).on(MaterialEvent.TO_CONCLUSION)
                .when(applicationContext.getBean(MaterialTransitionCondition.class))
                .perform(applicationContext.getBean(MaterialNodePassAction.class));
        /** 结论判定 >> 审核 >> 结论判定 */
        stateMachineBuilder.externalTransition()
                .from(MaterialState.CONCLUSION).to(MaterialState.FINAL_REVIEW).on(MaterialEvent.TO_REVIEW)
                .when(applicationContext.getBean(MaterialTransitionCondition.class))
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
