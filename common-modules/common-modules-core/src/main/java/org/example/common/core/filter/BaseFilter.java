package org.example.common.core.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.CaffeineRedisCache;
import org.example.common.core.domain.Token;
import org.example.common.core.domain.UserContextEntity;
import org.example.common.core.result.CommonResult;
import org.example.common.core.result.CommonServerResult;
import org.example.common.core.result.SystemServerResult;
import org.example.common.core.util.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 基础过滤器
 *
 * @author lihui
 * @since 2022/10/26
 */
@Slf4j
@WebFilter
@Component
@Order(Integer.MIN_VALUE)
public class BaseFilter implements Filter {
    @Value("${skip-urls}")
    private String skipUrls;
    @Resource
    private CaffeineRedisCache caffeineRedisCache;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // 简单请求直接放行
        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        // 校验是否是不需要验证token的url
        if (urlWhiteFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 校验请求中的token参数和数据
        String authorization = authorizationHeaderFilter(request);
        if (StrUtil.isEmpty(authorization)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(JSON.toJSONString(CommonResult.unauthorized()));
            return;
        }
        Token<UserContextEntity> token = TokenUtils.unsigned(authorization, UserContextEntity.class);
        UserContextEntity userContextEntity = token.getData();
        // 校验token
        if (!tokenFilter(authorization, userContextEntity.getId())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(JSON.toJSONString(CommonResult.unauthorized()));
            return;
        }
        // 校验资源
        if (!resourceFilter(request.getServletPath(), userContextEntity.getResourceIds())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(JSON.toJSONString(CommonResult.unauthorized()));
        }
        filterChain.doFilter(request, response);
    }

    private boolean urlWhiteFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 校验是否是不需要验证token的url
        return Arrays.stream(skipUrls.split(",")).anyMatch(o -> antPathMatcher.match(o, servletPath));
    }

    private String authorizationHeaderFilter(HttpServletRequest request) {
        // 校验请求中的token参数和数据
        String authorizationHeader = request.getHeader(CommonServerResult.AUTHORIZATION);
        String authorizationParameter = request.getParameter(CommonServerResult.AUTHORIZATION);
        return !StrUtil.isEmpty(authorizationHeader) ? authorizationHeader : authorizationParameter;
    }

    private boolean tokenFilter(String authorization, String userId) {
        // 校验请求中的token参数和数据
        if (StrUtil.isEmpty(userId)) {
            return false;
        }
        try {
            // token过期
            String token = caffeineRedisCache.get(SystemServerResult.USER_TOKEN_KEY + userId, String.class);
            return !StrUtil.isEmpty(token) && authorization.equals(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private boolean resourceFilter(String servletPath, List<String> resourceIds) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return resourceIds.stream().anyMatch(o -> antPathMatcher.match(o, servletPath));
    }
}