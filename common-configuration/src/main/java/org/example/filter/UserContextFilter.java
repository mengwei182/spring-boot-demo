package org.example.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.base.vo.TokenVo;
import org.example.entity.base.vo.UserInfoVo;
import org.example.usercontext.UserContext;
import org.example.util.TokenUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
@Component
public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorizationHeader = request.getHeader(BaseFilter.AUTHORIZATION);
        String authorizationParameter = request.getParameter(BaseFilter.AUTHORIZATION);
        String authorization = !StrUtil.isEmpty(authorizationHeader) ? authorizationHeader : authorizationParameter;
        try {
            TokenVo<UserInfoVo> tokenVo = TokenUtils.unsigned(authorization, UserInfoVo.class);
            UserInfoVo userInfoVo = tokenVo.getData();
            UserContext.set(userInfoVo.getId(), userInfoVo.getUsername(), userInfoVo);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}