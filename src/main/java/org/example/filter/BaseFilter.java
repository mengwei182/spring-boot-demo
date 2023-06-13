package org.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.example.properties.CommonProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * 基本过滤器
 *
 * @author lihui
 * @since 2022/10/26
 */
@Slf4j
@WebFilter
@Component
@Order(Integer.MIN_VALUE)
public class BaseFilter implements Filter {
    @Resource
    private CommonProperties commonProperties;
    public static final String AUTHORIZATION = "Authorization";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean filter = false;
        // 简单请求直接放行
        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filter = true;
        }
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String servletPath = request.getServletPath();
        // 校验是否是不需要验证token的url
        Optional<String> first = Arrays.stream(commonProperties.getUrlWhiteList().split(",")).filter(noAuthUrl -> antPathMatcher.match(noAuthUrl, servletPath)).findFirst();
        if (first.isPresent()) {
            response.setStatus(HttpServletResponse.SC_OK);
            filter = true;
        }
        if (filter) {
            request.getRequestDispatcher(servletPath).forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}