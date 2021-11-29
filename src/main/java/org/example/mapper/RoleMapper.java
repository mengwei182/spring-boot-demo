package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {
    void addRole(Role role);

    void deleteRole(String id);

    void updateRole(Role role);

    List<Role> getRolesByUserId(String userId);

    List<Role> getAllRoleList();

    Role getRoleByRoleId(String roleId);
}