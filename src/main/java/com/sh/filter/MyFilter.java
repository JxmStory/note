package com.sh.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 过滤器
 * 过滤器Filter是servlet的一种规范 只能用于web程序 依赖于servlet 在请求前后起作用
 * 执行顺序 filter->interceptor->Aop
 * @Date: 2019-5-20 15:41
 * @Auther: 季小沫的故事
 */
@WebFilter(urlPatterns = "/*", filterName = "requestFilter")
@Order(1)
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("This is filter -------- ");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "1800");
        filterChain.doFilter(servletRequest, servletResponse); //执行后续过滤器
        return;
    }

    @Override
    public void destroy() {

    }
}
