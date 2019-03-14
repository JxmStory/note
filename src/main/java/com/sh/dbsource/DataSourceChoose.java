package com.sh.dbsource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @param: 注解类
 * @return:
 * @date: 2018-11-27 18:44
 * @auther: 季小沫的故事
 */
@Retention(RetentionPolicy.RUNTIME) //元注解Retention
                                    //1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃
                                    //2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期
                                    //3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在

@Target(ElementType.METHOD)         //元注解Target 注解作用域
                                    // METHOD作用方法上 TYPE作用在类或接口上 PARAMETER作用在参数上
public @interface DataSourceChoose {
    String value();
}

