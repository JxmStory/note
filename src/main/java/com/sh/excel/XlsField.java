package com.sh.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: micomo
 * @Date: 2019/3/4 11:38
 * @Description: Excel 模板类字段注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XlsField {
    String name() default "";
}
