package org.example.security;

import com.alibaba.fastjson.JSON;
import org.example.common.global.StaticVariables;
import org.example.common.model.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader(StaticVariables.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, StaticVariables.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
        response.setHeader(StaticVariables.CACHE_CONTROL_HEADER, StaticVariables.CACHE_CONTROL_VALUE);
        response.setCharacterEncoding(StaticVariables.CHARACTER_ENCODING);
        response.setContentType(StaticVariables.CONTENT_TYPE);
        response.getWriter().println(JSON.toJSONString(CommonResult.unauthorized(authException.getMessage())));
        response.getWriter().flush();
    }
}