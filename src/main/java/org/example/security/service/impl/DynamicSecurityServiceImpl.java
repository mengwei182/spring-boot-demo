package org.example.security.service.impl;

import org.example.entity.Resource;
import org.example.mapper.ResourceMapper;
import org.example.security.service.DynamicSecurityService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicSecurityServiceImpl implements DynamicSecurityService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @Override
    public Map<String, ConfigAttribute> loadSource() {
        List<Resource> resources = resourceMapper.getAllResourceList();
        Map<String, ConfigAttribute> configAttributeMap = new HashMap<>();
        for (Resource resource : resources) {
            configAttributeMap.put(resource.getUrl(), new SecurityConfig(resource.getId()));
        }
        return configAttributeMap;
    }
}