package org.example.cache.impl;

import org.example.cache.RoleResourceRelationCacheService;
import org.example.entity.RoleResourceRelation;
import org.example.mapper.RoleResourceRelationMapper;
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
 * @since 2022/10/29
 */
@Service
public class RoleResourceRelationCacheServiceImpl implements RoleResourceRelationCacheService {
    @Resource
    private RedisService redisService;
    @Resource
    private RoleResourceRelationMapper roleResourceRelationMapper;
    private static final String ROLE_RESOURCE_RELATION_CACHE = "ROLE_RESOURCE_RELATION_CACHE";

    @Override
    public List<RoleResourceRelation> getRoleResourceRelations() {
        List<RoleResourceRelation> roleResourceRelations = new ArrayList<>();
        Set<Object> members = redisService.getSetOperations().members(ROLE_RESOURCE_RELATION_CACHE);
        if (!CollectionUtils.isEmpty(members)) {
            for (Object member : members) {
                roleResourceRelations.add(CommonUtils.gson().fromJson(CommonUtils.gson().toJson(member), RoleResourceRelation.class));
            }
        } else {
            roleResourceRelations = roleResourceRelationMapper.selectList(null);
            redisService.getSetOperations().add(ROLE_RESOURCE_RELATION_CACHE, roleResourceRelations.toArray());
        }
        return roleResourceRelations;
    }

    @Override
    public void setRoleResourceRelations(List<RoleResourceRelation> roleResourceRelations) {
        redisService.getSetOperations().add(ROLE_RESOURCE_RELATION_CACHE, roleResourceRelations.toArray());
    }
}