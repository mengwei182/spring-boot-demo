package org.example.service;

import org.example.common.model.CommonResult;
import org.example.entity.Role;
import org.example.entity.RoleMenuRelation;

public interface RoleService {
    CommonResult addRole(Role role);

    CommonResult deleteRole(String id);

    CommonResult updateRole(Role role);

    CommonResult updateRoleMenu(RoleMenuRelation roleMenuRelation);
}