package com.sh.log;

import com.sh.common.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    //缓存请求开始时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 声明切入点是controller下面所有类的方法
     */
    @Pointcut("execution(public * com.sh.controller..*.*(..))")
    public void aspectPointCut(){}

    @Before("aspectPointCut()")
    public void befor(JoinPoint joinPoint){

        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("URL : {}", request.getRequestURL());
        logger.info("HTTP_METHOD : {}", request.getMethod());
        logger.info("IP : {}", request.getRemoteAddr());
        logger.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "result", pointcut = "aspectPointCut()")
    public void after(Result result){

        logger.info("RESPONSE : {}", result.toString());
        logger.info("SPEND_TIME : {}", System.currentTimeMillis() - startTime.get());
        startTime.remove();

    }

}
