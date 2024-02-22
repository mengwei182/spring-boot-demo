package org.example.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.CaffeineRedisCache;
import org.example.entity.base.Token;
import org.example.entity.system.vo.ResourceVO;
import org.example.entity.system.vo.UserVO;
import org.example.model.CommonResult;
import org.example.result.CommonServerResult;
import org.example.result.SystemServerResult;
import org.example.util.GsonUtils;
import org.example.util.TokenUtils;
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
import java.util.Optional;

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
            response.getWriter().print(GsonUtils.gson().toJson(CommonResult.unauthorized()));
            return;
        }
        Token<UserVO> token = TokenUtils.unsigned(authorization, UserVO.class);
        UserVO userVO = token.getData();
        // 校验token
        if (!tokenFilter(authorization, userVO)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(GsonUtils.gson().toJson(CommonResult.unauthorized()));
            return;
        }
        // 校验资源
        if (!resourceFilter(userVO, request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(GsonUtils.gson().toJson(CommonResult.unauthorized()));
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

    private boolean tokenFilter(String authorization, UserVO userVO) {
        // 校验请求中的token参数和数据
        if (userVO == null) {
            return false;
        }
        try {
            // token过期
            String token = caffeineRedisCache.get(SystemServerResult.USER_TOKEN_KEY + userVO.getId(), String.class);
            return !StrUtil.isEmpty(token) && authorization.equals(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private boolean resourceFilter(UserVO userVO, HttpServletRequest request) {
        String servletPath = request.getServletPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<ResourceVO> resourceVOS = userVO.getResources();
        if (CollectionUtil.isEmpty(resourceVOS)) {
            return false;
        }
        Optional<ResourceVO> findAny = resourceVOS.stream().filter(o -> antPathMatcher.match(o.getUrl(), servletPath)).findAny();
        return findAny.isPresent();
    }
}