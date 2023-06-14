package org.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.vo.TokenVo;
import org.example.entity.vo.UserInfoVo;
import org.example.usercontext.UserContext;
import org.example.util.TokenUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户上下文过滤器
 *
 * @author lihui
 * @since 2022/10/26
 */
@Slf4j
@Order
@WebFilter
public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorizationHeader = request.getHeader(BaseFilter.AUTHORIZATION);
        String authorizationParameter = request.getParameter(BaseFilter.AUTHORIZATION);
        String authorization = StringUtils.hasLength(authorizationHeader) ? authorizationHeader : authorizationParameter;
        TokenVo<UserInfoVo> tokenVo = TokenUtils.unsigned(authorization, UserInfoVo.class);
        UserInfoVo userInfoVo = tokenVo.getData();
        UserContext.set(userInfoVo.getId(), userInfoVo.getUsername(), userInfoVo);
        filterChain.doFilter(request, response);
    }
}