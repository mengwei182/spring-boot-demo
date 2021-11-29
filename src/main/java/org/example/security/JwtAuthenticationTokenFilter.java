package org.example.security;

import lombok.extern.slf4j.Slf4j;
import org.example.common.model.CustomUserDetails;
import org.example.security.service.JwtTokenService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Resource
    private UserService userService;
    @Resource
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(tokenHeader);
        if (authToken != null && authToken.startsWith(this.tokenHead)) {
            String username = jwtTokenService.getUsername(authToken);
            log.info("authentication checking username:{}", username);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (username != null && securityContext.getAuthentication() == null) {
                CustomUserDetails customUserDetails = (CustomUserDetails) userService.loadUserByUsername(username);
                if (jwtTokenService.validateToken(authToken, customUserDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated username:{}", username);
                    securityContext.setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}