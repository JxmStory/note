package com.sh.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if (uri.startsWith("/api")) {
            String method = request.getParameter("method");
            if (StringUtils.isNotBlank(method)) {
                String path = uri + "/" + method;
                request.getRequestDispatcher(path).forward(servletRequest,servletResponse);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse); //执行后续过滤器
        return;
    }

    @Override
    public void destroy() {

    }
}
