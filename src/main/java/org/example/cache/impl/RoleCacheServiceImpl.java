package org.example.cache.impl;

import org.example.cache.RoleCacheService;
import org.example.entity.Role;
import org.example.mapper.RoleMapper;
import org.example.redis.RedisService;
import org.example.util.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lihui
 * @since 2022/10/30
 */
@Service
public class RoleCacheServiceImpl implements RoleCacheService {
    @Resource
    private RedisService redisService;
    @Resource
    private RoleMapper roleMapper;
    private static final String ROLE_CACHE_PREFIX = "ROLE_CACHE_PREFIX_";

    @Override
    public List<Role> getRoleByUserId(String userId) {
        List<Role> roles = new ArrayList<>();
        Set<Object> members = redisService.getSetOperations().members(ROLE_CACHE_PREFIX + userId);
        if (!CollectionUtils.isEmpty(members)) {
            for (Object member : members) {
                roles.add(CommonUtils.gson().fromJson(CommonUtils.gson().toJson(member), Role.class));
            }
        } else {
            roles = roleMapper.selectList(null);
            redisService.getSetOperations().add(ROLE_CACHE_PREFIX + userId, roles.toArray());
        }
        return roles;
    }

    @Override
    public void setRoleByUserId(String userId, List<Role> roles) {
        redisService.getSetOperations().add(ROLE_CACHE_PREFIX + userId, roles.toArray());
    }
}