package org.example.filter;

import org.example.entity.vo.TokenVo;
import org.example.entity.vo.UserInfoVo;
import org.example.error.CommonServerResult;
import org.example.global.ResultCode;
import org.example.model.CommonResult;
import org.example.util.CommonUtils;
import org.example.util.TokenUtils;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器
 *
 * @author lihui
 * @since 2022/10/26
 */
@Order(1)
@WebFilter
@Component
public class TokenFilter implements Filter {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String authorizationHeader = request.getHeader(BaseFilter.AUTHORIZATION);
        String authorizationParameter = request.getParameter(BaseFilter.AUTHORIZATION);
        String authorization = StringUtils.hasLength(authorizationHeader) ? authorizationHeader : authorizationParameter;
        if (!StringUtils.hasLength(authorization)) {
            response.setStatus(ResultCode.UNAUTHORIZED.getCode());
            response.getWriter().print(CommonUtils.gson().toJson(CommonResult.unauthorized()));
            return;
        }
        // 校验请求中的token参数和数据
        TokenVo<UserInfoVo> tokenVo = TokenUtils.unsigned(authorization, UserInfoVo.class);
        UserInfoVo userInfoVo = tokenVo.getData();
        if (userInfoVo == null) {
            response.setStatus(ResultCode.UNAUTHORIZED.getCode());
            response.getWriter().print(CommonUtils.gson().toJson(CommonResult.unauthorized()));
            return;
        }
        // token过期
        Object token = redisTemplate.opsForValue().get(userInfoVo.getId());
        if (token == null) {
            response.setStatus(ResultCode.UNAUTHORIZED.getCode());
            response.getWriter().print(CommonUtils.gson().toJson(CommonResult.error(CommonServerResult.TOKEN_TIME_OUT)));
            return;
        }
        filterChain.doFilter(request, response);
    }
}