package org.example.service;

import org.example.entity.vo.RoleMenuRelationVo;
import org.example.entity.vo.RoleVo;

public interface RoleService {
    Boolean addRole(RoleVo roleVo);

    Boolean deleteRole(String id);

    Boolean updateRole(RoleVo roleVo);

    Boolean updateRoleMenu(RoleMenuRelationVo roleMenuRelationVo);
}