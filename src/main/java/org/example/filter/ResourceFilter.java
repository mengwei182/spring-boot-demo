package org.example.filter;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.vo.ResourceVo;
import org.example.entity.vo.TokenVo;
import org.example.entity.vo.UserInfoVo;
import org.example.error.CommonErrorResult;
import org.example.error.exception.CommonException;
import org.example.util.TokenUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 资源过滤器
 *
 * @author lihui
 * @since 2022/10/26
 */
@Slf4j
@Order(2)
@WebFilter
@Component
public class ResourceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorizationHeader = request.getHeader(BaseFilter.AUTHORIZATION);
        String authorizationParameter = request.getParameter(BaseFilter.AUTHORIZATION);
        String authorization = StringUtils.hasLength(authorizationHeader) ? authorizationHeader : authorizationParameter;
        TokenVo<UserInfoVo> tokenVo = TokenUtils.unsigned(authorization, UserInfoVo.class);
        UserInfoVo userInfoVo = tokenVo.getData();
        List<ResourceVo> resourceVos = userInfoVo.getResources();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String servletPath = request.getServletPath();
        Optional<ResourceVo> findAny = resourceVos.stream().filter(o -> antPathMatcher.match(o.getUrl(), servletPath)).findAny();
        if (findAny.isPresent()) {
            filterChain.doFilter(request, response);
        } else {
            throw new CommonException(CommonErrorResult.UNAUTHORIZED);
        }
    }
}