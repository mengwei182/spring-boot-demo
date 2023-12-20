package org.example.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.base.vo.TokenVo;
import org.example.entity.base.vo.UserInfoVo;
import org.example.entity.system.vo.ResourceVo;
import org.example.model.CommonResult;
import org.example.properties.CommonProperties;
import org.example.util.GsonUtils;
import org.example.util.TokenUtils;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Resource
    private CommonProperties commonProperties;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    public static final String AUTHORIZATION = "Authorization";
    private static final String USER_TOKEN_KEY = "USER_TOKEN_KEY_";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-type", "application/json");
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
        TokenVo<UserInfoVo> tokenVo = TokenUtils.unsigned(authorization, UserInfoVo.class);
        UserInfoVo userInfoVo = tokenVo.getData();
        // 校验token
        if (!tokenFilter(authorization, userInfoVo)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(GsonUtils.gson().toJson(CommonResult.unauthorized()));
            return;
        }
        // 校验资源
        if (!resourceFilter(userInfoVo, request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(GsonUtils.gson().toJson(CommonResult.unauthorized()));
        }
        filterChain.doFilter(request, response);
    }

    private boolean urlWhiteFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 校验是否是不需要验证token的url
        Optional<String> first = Arrays.stream(commonProperties.getSkipUrl().split(",")).filter(o -> antPathMatcher.match(o, servletPath)).findFirst();
        return first.isPresent();
    }

    private String authorizationHeaderFilter(HttpServletRequest request) {
        // 校验请求中的token参数和数据
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String authorizationParameter = request.getParameter(AUTHORIZATION);
        return !StrUtil.isEmpty(authorizationHeader) ? authorizationHeader : authorizationParameter;
    }

    private boolean tokenFilter(String authorization, UserInfoVo userInfoVo) {
        // 校验请求中的token参数和数据
        if (userInfoVo == null) {
            return false;
        }
        try {
            // token过期
            String token = (String) redisTemplate.opsForValue().get(USER_TOKEN_KEY + userInfoVo.getId());
            return !StrUtil.isEmpty(token) && authorization.equals(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private boolean resourceFilter(UserInfoVo userInfoVo, HttpServletRequest request) {
        String servletPath = request.getServletPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        List<ResourceVo> resourceVos = userInfoVo.getResources();
        Optional<ResourceVo> findAny = resourceVos.stream().filter(o -> antPathMatcher.match(o.getUrl(), servletPath)).findAny();
        return findAny.isPresent();
    }
}