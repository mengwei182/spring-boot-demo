package org.example.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.example.cache.ResourceCacheService;
import org.example.cache.RoleCacheService;
import org.example.cache.RoleResourceRelationCacheService;
import org.example.common.exception.CommonException;
import org.example.common.global.GlobalResultVariables;
import org.example.entity.Role;
import org.example.entity.RoleResourceRelation;
import org.example.entity.vo.TokenVo;
import org.example.properties.ConfigProperties;
import org.example.util.TokenUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lihui
 * @since 2022/10/26
 */
@Slf4j
@Order(2)
@Component
public class ResourceInterceptor implements HandlerInterceptor {
    @Resource
    private ConfigProperties configProperties;
    @Resource
    private RoleCacheService roleCacheService;
    @Resource
    private ResourceCacheService resourceCacheService;
    @Resource
    private RoleResourceRelationCacheService roleResourceRelationCacheService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        // 简单请求直接放行
        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String servletPath = request.getServletPath();
        if (StringUtils.hasLength(servletPath)) {
            // 校验是否是不需要验证token的url
            String[] noAuthUrls = configProperties.getNoAuthUrls().split(",");
            for (String noAuthUrl : noAuthUrls) {
                if (servletPath.startsWith(noAuthUrl)) {
                    return true;
                }
            }
        } else {
            throw new CommonException(GlobalResultVariables.UNAUTHORIZED);
        }
        String cookie = request.getHeader("Cookie");
        TokenVo<?> tokenVo = TokenUtil.unsigned(cookie);
        String userId = (String) tokenVo.getId();
        List<Role> roles = roleCacheService.getRoleByUserId(userId);
        Map<String, Role> roleMap = roles.stream().collect(Collectors.toMap(Role::getId, o -> o));
        List<org.example.entity.Resource> resources = resourceCacheService.getResources();
        Map<String, org.example.entity.Resource> ResourceMap = resources.stream().collect(Collectors.toMap(org.example.entity.Resource::getId, o -> o));
        List<RoleResourceRelation> roleResourceRelations = roleResourceRelationCacheService.getRoleResourceRelations();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (RoleResourceRelation roleResourceRelation : roleResourceRelations) {
            Role role = roleMap.get(roleResourceRelation.getRoleId());
            if (role != null) {
                org.example.entity.Resource resource = ResourceMap.get(roleResourceRelation.getResourceId());
                if (antPathMatcher.match(resource.getUrl(), servletPath)) {
                    return true;
                }
            }
        }
        throw new CommonException(GlobalResultVariables.UNAUTHORIZED);
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
    }
}