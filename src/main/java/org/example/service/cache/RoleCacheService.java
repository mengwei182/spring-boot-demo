package org.example.service.cache;

import org.example.entity.Role;

import java.util.List;

public interface RoleCacheService {
    List<Role> getRoleByUserId(String userId);

    void setRoleByUserId(String userId, List<Role> roles);
}