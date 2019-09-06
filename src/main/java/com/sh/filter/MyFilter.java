package com.sh.filter;

import com.alibaba.fastjson.JSONObject;
import com.sh.utils.StreamUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import sun.rmi.runtime.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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

    private Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        logger.info("【过滤器】对请求进行路由判断");
        /*
         * 使用自定义装饰类MyRequestWrapper对ServletRequest进行包装
         *
         */
        ServletRequest requestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest);
        HttpServletRequest request = (HttpServletRequest) requestWrapper;
        String uri = request.getRequestURI();
        if (uri.startsWith("/api")) {
            String method = "";
            if (MediaType.APPLICATION_JSON_VALUE.equals(requestWrapper.getContentType())) {
                //获取json
                String paramJson = StreamUtil.getBodyString(requestWrapper);
                JSONObject json = JSONObject.parseObject(paramJson);
                if (json != null) {
                    method = json.getString("method");
                }
            } else {
                method = request.getParameter("method");
            }
            if (StringUtils.isNotBlank(method)) {
                String path = uri + "/" + method;
                logger.info("【过滤器】method={},转向{}", method, path);
                request.getRequestDispatcher(path).forward(requestWrapper,servletResponse);
                return;
            }
        }
        filterChain.doFilter(requestWrapper, servletResponse); //执行后续过滤器
        return;
    }

    @Override
    public void destroy() {

    }
}
