package org.example.filter;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.vo.TokenVo;
import org.example.entity.vo.UserInfoVo;
import org.example.error.CommonErrorResult;
import org.example.error.exception.CommonException;
import org.example.util.TokenUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * token过滤器
 *
 * @author lihui
 * @since 2022/10/26
 */
@Slf4j
@Order(1)
@WebFilter
@Component
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorizationHeader = request.getHeader(BaseFilter.AUTHORIZATION);
        String authorizationParameter = request.getParameter(BaseFilter.AUTHORIZATION);
        String authorization = StringUtils.hasLength(authorizationHeader) ? authorizationHeader : authorizationParameter;
        TokenVo<UserInfoVo> tokenVo = TokenUtils.unsigned(authorization, UserInfoVo.class);
        // 校验token有效时间
        if (tokenVo.getSignTime().getTime() + tokenVo.getExpiration() * 1000 < new Date().getTime()) {
            throw new CommonException(CommonErrorResult.TOKEN_TIME_OUT);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}