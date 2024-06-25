package org.example.common.core.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.CaffeineRedisCache;
import org.example.common.core.constant.CommonConstant;
import org.example.common.core.domain.LoginUser;
import org.example.common.core.domain.Token;
import org.example.common.core.result.CommonResult;
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
import java.util.Date;
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
public class AuthenticationFilter implements Filter {
    @Value("${skip-urls}")
    private String skipUrls;
    @Resource
    private CaffeineRedisCache caffeineRedisCache;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
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
            writeUnauthorized(response);
            return;
        }
        Token<LoginUser> token;
        try {
            token = TokenUtils.unsigned(authorization, LoginUser.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            writeUnauthorized(response);
            return;
        }
        LoginUser loginUser = token.getData();
        // token是否有效
        if (!tokenValid(authorization, loginUser.getId())) {
            writeUnauthorized(response);
            return;
        }
        // resource是否有效
        if (!resourceValid(request.getServletPath(), loginUser.getResourceUrls())) {
            writeUnauthorized(response);
            return;
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
        String authorizationHeader = request.getHeader(CommonConstant.AUTHORIZATION);
        String authorizationParameter = request.getParameter(CommonConstant.AUTHORIZATION);
        return !StrUtil.isEmpty(authorizationHeader) ? authorizationHeader : authorizationParameter;
    }

    private boolean tokenValid(String authorization, String userId) {
        // 校验请求中的token参数和数据
        if (StrUtil.isEmpty(userId)) {
            return false;
        }
        try {
            // token是否有效
            String tokenString = caffeineRedisCache.get("USER_TOKEN_KEY_" + userId, String.class);
            boolean tokenValid = !StrUtil.isEmpty(tokenString) && authorization.equals(tokenString);
            // token是否过期
            Token<?> token = TokenUtils.unsigned(authorization);
            Date currentDate = new Date();
            Date expirationDate = token.getExpirationDate();
            boolean tokenExpiration = expirationDate.getTime() > currentDate.getTime();
            return tokenValid && tokenExpiration;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private boolean resourceValid(String servletPath, List<String> resourceUrls) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return resourceUrls.stream().anyMatch(o -> antPathMatcher.match(o, servletPath));
    }

    private void writeUnauthorized(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(JSON.toJSONString(CommonResult.unauthorized()));
    }
}