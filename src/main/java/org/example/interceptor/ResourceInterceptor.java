package org.example.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.vo.ResourceVo;
import org.example.entity.vo.TokenVo;
import org.example.entity.vo.UserInfoVo;
import org.example.error.CommonErrorResult;
import org.example.error.exception.CommonException;
import org.example.properties.CommonProperties;
import org.example.util.TokenUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author lihui
 * @since 2022/10/26
 */
@Slf4j
@Order(2)
@Component
public class ResourceInterceptor implements HandlerInterceptor {
    @Resource
    private CommonProperties commonProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 简单请求直接放行
        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String servletPath = request.getServletPath();
        // 校验是否是不需要验证token的url
        Optional<String> first = Arrays.stream(commonProperties.getUrlWhiteList().split(",")).filter(noAuthUrl -> antPathMatcher.match(noAuthUrl, servletPath)).findFirst();
        if (first.isPresent()) {
            return true;
        }
        String cookie = request.getHeader("Cookie");
        TokenVo<UserInfoVo> tokenVo = TokenUtils.unsigned(cookie, UserInfoVo.class);
        // 经过AuthorizationInterceptor拦截器处理后，token不会为null
        UserInfoVo userInfoVo = tokenVo.getData();
        List<ResourceVo> resourceVos = userInfoVo.getResources();
        Optional<ResourceVo> findAny = resourceVos.stream().filter(o -> antPathMatcher.match(o.getUrl(), servletPath)).findAny();
        if (findAny.isPresent()) {
            return true;
        }
        throw new CommonException(CommonErrorResult.UNAUTHORIZED);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}