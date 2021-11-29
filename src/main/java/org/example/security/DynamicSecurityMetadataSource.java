package org.example.security;

import lombok.extern.slf4j.Slf4j;
import org.example.security.service.DynamicSecurityService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Slf4j
@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static Map<String, ConfigAttribute> configAttributeMap = new HashMap<>();
    @Resource
    private DynamicSecurityService dynamicSecurityService;

    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = dynamicSecurityService.loadSource();
    }

    public void clearDataSource() {
        configAttributeMap.clear();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        if (CollectionUtils.isEmpty(configAttributeMap)) {
            return configAttributes;
        }
        try {
            URI uri = new URI(((FilterInvocation) o).getRequestUrl());
            String path = uri.getPath();
            PathMatcher pathMatcher = new AntPathMatcher();
            for (String pattern : configAttributeMap.keySet()) {
                if (pathMatcher.match(pattern, path)) {
                    configAttributes.add(configAttributeMap.get(pattern));
                }
            }
        } catch (URISyntaxException e) {
            log.error("get attributes error:{}", e.getMessage());
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return configAttributeMap.values();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}