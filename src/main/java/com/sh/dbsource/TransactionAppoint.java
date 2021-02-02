package com.sh.dbsource;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  自定义事务注解
 *  @author micomo
 *  @date 2020/12/28 11:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
@interface TransactionAppoint {

    String[] value() default {};

}
