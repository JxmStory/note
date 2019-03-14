package com.sh.dbsource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @description: 
 * @param: 切面类
 * @return: 
 * @date: 2018-11-27 18:50
 * @auther: 季小沫的故事
 */

@Aspect //使用@Aspect声明切面
@Component //@Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
public class DataSourceAspect  {

    private static Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(public * com.sh.service..*.*(..))") //声明切入点是service下面所有类的方法
    public void webLog(){}

    @Before("webLog()") //在切入点方法执行前执行
    public void before(JoinPoint point) {

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
                DataSourceChoose data = m.getAnnotation(DataSourceChoose.class);//获取注解对象
                DynamicDataSourceHolder.putDataSource(data.value());//将注解value的值放入本地线程副本中ThreadLocal
                LOGGER.info("========获取的数据源为: {}=========", data.value());
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}