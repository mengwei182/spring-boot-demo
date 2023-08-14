package org.example.service;

import org.example.entity.system.vo.RoleMenuRelationVo;
import org.example.entity.system.vo.RoleVo;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface RoleService {
    /**
     * 新增角色
     *
     * @param roleVo
     * @return
     */
    Boolean addRole(RoleVo roleVo);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    Boolean deleteRole(String id);

    /**
     * 更新角色
     *
     * @param roleVo
     * @return
     */
    Boolean updateRole(RoleVo roleVo);

    /**
     * 新增角色菜单关系
     *
     * @param roleMenuRelationVo
     * @return
     */
    Boolean addRoleMenu(RoleMenuRelationVo roleMenuRelationVo);
}