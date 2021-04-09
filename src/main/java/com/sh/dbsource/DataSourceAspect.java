package com.sh.dbsource;

import com.sh.common.MyException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面类
 * 2018-11-27 18:50
 * @author 季小沫的故事
 */
@Aspect
@Component
public class DataSourceAspect  {

    private static Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 功能描述: 声明切入点是service下面所有类的方法
     */
    @Pointcut("execution(public * com.sh.service..*.*(..))")
    public void webLog(){}

    /**
     * 功能描述: 在切入点方法执行前执行
     */
    @Before("webLog()")
    public void before(JoinPoint point) {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<? extends Object> targetClass = point.getTarget().getClass();
        DataSourceChoose targetDataSource = targetClass.getAnnotation(DataSourceChoose.class);

        //获取切入点所在类的对象 UserServiceImpl
        Object target = point.getTarget();
        //获取切入点的方法名 public Result update()
        String method = point.getSignature().getName();

        //获取切入点所在类实现的接口集合 [UserServiceInter]
        Class<?>[] classz = target.getClass().getInterfaces();
        //获取切入点方法的参数集合 [user]
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        try {
            //获取切入点在对应接口中(该类实现的第一个接口)的方法
            Method m = classz[0].getMethod(method, parameterTypes);
            //如果该方法上有DataSource的注解
            if (m != null && m.isAnnotationPresent(DataSourceChoose.class)) {
                //获取注解对象
                DataSourceChoose data = m.getAnnotation(DataSourceChoose.class);
                //将注解value的值放入本地线程副本中ThreadLocal
                DynamicDataSourceHolder.putDataSource(data.value());
                LOGGER.info("========获取的数据源为: {}=========", data.value());
            }
        } catch (Exception e) {
            throw new MyException("获取数据源异常");
        }
    }

    @After("webLog()")
    public void after() {
        LOGGER.info("========移除当前线程数据源=========");
        DynamicDataSourceHolder.removeDataSource();
    }


//    /**
//     *  以下方法通过注解获取切入点
//     *  @author micomo
//     *  @date 2021/4/8 18:46
//     */
//    @Pointcut("@annotation(com.sh.dbsource.DataSourceChoose)"
//            + "|| @within(com.sh.dbsource.DataSourceChoose)")
//    public void annotationCut() {}
//
//    @Around("annotationCut()")
//    public void annotationAround(ProceedingJoinPoint point) {
//
//        // 获取类上的注解DataSourceChoose
//        Class<? extends Object> targetClass = point.getTarget().getClass();
//        DataSourceChoose targetDataSource = targetClass.getAnnotation(DataSourceChoose.class);
//        if (targetDataSource == null) {
//            // 类上没有注解时 获取方法上的注解DataSourceChoose
//            MethodSignature signature = (MethodSignature) point.getSignature();
//            Method method = signature.getMethod();
//            targetDataSource = method.getAnnotation(DataSourceChoose.class);
//        }
//        if (targetDataSource != null) {
//            LOGGER.info("========获取的数据源为: {}=========", targetDataSource.value());
//            DynamicDataSourceHolder.putDataSource(targetDataSource.value());
//        }
//        try {
//            point.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            // 销毁数据源 在执行方法之后
//            DynamicDataSourceHolder.removeDataSource();
//        }
//    }
}