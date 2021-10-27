package com.sh.dbsource;

import com.sh.utils.SpringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Stack;

@Aspect
@Configuration
public class TransactionAspect {

    private static final Logger logger = LoggerFactory.getLogger(TransactionAspect.class);


    @Pointcut("@annotation(com.sh.dbsource.TransactionAppoint)")
    public void transactionAppoint() {
    }


    @Pointcut("execution(* com.sh.juc..*.*(..))")
    public void excudeController() {
    }


    @Around(value = "transactionAppoint()&&excudeController()&&@annotation(annotation)")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint, TransactionAppoint annotation) throws Throwable {
        Stack<DataSourceTransactionManager> dstmStack = new Stack<DataSourceTransactionManager>();
        Stack<TransactionStatus> tsStack = new Stack<TransactionStatus>();
        try {
            if (!openTransaction(dstmStack, tsStack, annotation)) {
                return null;
            }
            Object ret = thisJoinPoint.proceed();
            commit(dstmStack, tsStack);
            return ret;
        } catch (Throwable e) {
            rollback(dstmStack, tsStack);
            logger.error(String.format("【事务管理切面】, 方法:%s-%s 出现异常:",
                    thisJoinPoint.getTarget().getClass().getSimpleName(), thisJoinPoint.getSignature().getName()), e);
            throw e;
        }
    }

    /**
     * 开启事务处理方法
     *
     * @param dstmStack
     * @param tsStack
     * @param annotation
     * @return
     */
    private boolean openTransaction(Stack<DataSourceTransactionManager> dstmStack,
                                    Stack<TransactionStatus> tsStack, TransactionAppoint annotation) {

        String[] managerNames = annotation.value();
        if (ArrayUtils.isEmpty(annotation.value())) {
            return false;
        }

        for (String beanName : managerNames) {
            DataSourceTransactionManager manager = (DataSourceTransactionManager) SpringUtils.getBean(beanName);
            TransactionStatus transactionStatus = manager.getTransaction(new DefaultTransactionDefinition());
            tsStack.push(transactionStatus);
            dstmStack.push(manager);
        }
        return true;
    }

    /**
     * 提交处理方法
     *
     * @param dstmStack
     * @param tsStack
     */
    private void commit(Stack<DataSourceTransactionManager> dstmStack,
                        Stack<TransactionStatus> tsStack) {
        while (!dstmStack.isEmpty()) {
            dstmStack.pop().commit(tsStack.pop());
        }
    }

    /**
     * 回滚处理方法
     *
     * @param dstmStack
     * @param tsStack
     */
    private void rollback(Stack<DataSourceTransactionManager> dstmStack,
                          Stack<TransactionStatus> tsStack) {
        while (!dstmStack.isEmpty()) {
            dstmStack.pop().rollback(tsStack.pop());
        }
    }
}