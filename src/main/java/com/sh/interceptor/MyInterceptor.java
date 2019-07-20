package com.sh.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 自定义拦截器 需要在springboot启动类配置扫描器
 * 拦截器是基于AOP实现的 依赖于Spring框架 可以应用于Application/Swing
 * 拦截器是Spring的组件 可以注入spring容器中的bean 可以深入到方法前后 方法异常前后
 * 执行顺序 filter->interceptor->Aop
 * @Date: 2019-5-21 10:41
 * @Auther: 季小沫的故事
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("This is preHandle -------------");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("This is postHandle -------------");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("This is afterCompletion -------------");
    }
}
