package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.query.RoleQueryPage;
import org.example.entity.system.vo.RoleMenuRelationVO;
import org.example.entity.system.vo.RoleVO;

import java.util.List;

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
    Boolean addRole(RoleVO roleVo);

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
    Boolean updateRole(RoleVO roleVo);

    /**
     * 新增角色菜单关系
     *
     * @param roleMenuRelationVo
     * @return
     */
    Boolean addRoleMenu(RoleMenuRelationVO roleMenuRelationVo);

    /**
     * 查询角色列表
     *
     * @param queryPage
     * @return
     */
    Page<RoleVO> getRoleList(RoleQueryPage queryPage);

    /**
     * 查询所有角色列表
     *
     * @return
     */
    List<RoleVO> getAllRoleList();
}