package com.sh.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration //标记为配置类，本质上是将该类纳入上下文从而执行addInterceptor动作，故@Component注解亦可
public class MyInterceptorConfigure extends WebMvcConfigurerAdapter {
    //继承WebMVCConfigureAdapter，并覆盖 addInterceptror 方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor());
        super.addInterceptors(registry);
    }
}