package org.example.cache.impl;

import org.example.cache.ResourceCacheService;
import org.example.entity.Resource;
import org.example.mapper.ResourceMapper;
import org.example.redis.RedisService;
import org.example.util.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lihui
 * @since 2022/10/30
 */
@Service
public class ResourceCacheServiceImpl implements ResourceCacheService {
    @javax.annotation.Resource
    private RedisService redisService;
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    private static final String RESOURCE_CACHE = "RESOURCE_CACHE";

    @Override
    public List<Resource> getResources() {
        List<Resource> resources = new ArrayList<>();
        Set<Object> members = redisService.getSetOperations().members(RESOURCE_CACHE);
        if (!CollectionUtils.isEmpty(members)) {
            for (Object member : members) {
                resources.add(CommonUtils.gson().fromJson(CommonUtils.gson().toJson(member), Resource.class));
            }
        } else {
            resources = resourceMapper.selectList(null);
            redisService.getSetOperations().add(RESOURCE_CACHE, resources.toArray());
        }
        return resources;
    }

    @Override
    public void setResource(List<Resource> resources) {
        redisService.getSetOperations().add(RESOURCE_CACHE, resources.toArray());
    }
}