package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.entity.system.Role;
import org.example.entity.system.RoleMenuRelation;
import org.example.entity.system.UserRoleRelation;
import org.example.entity.system.vo.RoleMenuRelationVo;
import org.example.entity.system.vo.RoleVo;
import org.example.error.CommonServerResult;
import org.example.error.SystemServerResult;
import org.example.error.exception.CommonException;
import org.example.mapper.RoleMapper;
import org.example.mapper.RoleMenuRelationMapper;
import org.example.mapper.UserRoleRelationMapper;
import org.example.service.RoleService;
import org.example.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;
    @Resource
    private RoleMenuRelationMapper roleMenuRelationMapper;

    /**
     * 新增角色
     *
     * @param roleVo
     * @return
     */
    @Override
    public Boolean addRole(RoleVo roleVo) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVo, role);
        role.setId(CommonUtils.uuid());
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        Role resultRole = roleMapper.selectOne(queryWrapper.eq(Role::getName, roleVo.getName()));
        if (resultRole != null) {
            throw new CommonException(SystemServerResult.ROLE_NAME_EXIST);
        }
        roleMapper.insert(role);
        return true;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteRole(String id) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new CommonException(CommonServerResult.OBJECT_NOT_EXIST);
        }
        roleMapper.deleteById(id);
        QueryWrapper<UserRoleRelation> userRoleRelationQueryWrapper = new QueryWrapper<>();
        userRoleRelationQueryWrapper.lambda().eq(UserRoleRelation::getRoleId, id);
        userRoleRelationMapper.delete(userRoleRelationQueryWrapper);
        QueryWrapper<RoleMenuRelation> roleMenuRelationQueryWrapper = new QueryWrapper<>();
        roleMenuRelationQueryWrapper.lambda().eq(RoleMenuRelation::getRoleId, id);
        roleMenuRelationMapper.delete(roleMenuRelationQueryWrapper);
        return true;
    }

    /**
     * 更新角色
     *
     * @param roleVo
     * @return
     */
    @Override
    public Boolean updateRole(RoleVo roleVo) {
        Role role = roleMapper.selectById(roleVo.getId());
        if (role == null) {
            throw new CommonException(CommonServerResult.OBJECT_NOT_EXIST);
        }
        role = new Role();
        BeanUtils.copyProperties(roleVo, role);
        roleMapper.updateById(role);
        return true;
    }

    /**
     * 新增角色菜单关系
     *
     * @param roleMenuRelationVo
     * @return
     */
    @Override
    @Transactional
    public Boolean addRoleMenu(RoleMenuRelationVo roleMenuRelationVo) {
        Role role = roleMapper.selectById(roleMenuRelationVo.getRoleId());
        if (role == null) {
            throw new CommonException(CommonServerResult.OBJECT_NOT_EXIST);
        }
        QueryWrapper<RoleMenuRelation> roleMenuRelationQueryWrapper = new QueryWrapper<>();
        roleMenuRelationQueryWrapper.lambda().eq(RoleMenuRelation::getRoleId, roleMenuRelationVo.getRoleId());
        roleMenuRelationMapper.delete(roleMenuRelationQueryWrapper);
        List<String> menuIds = roleMenuRelationVo.getMenuIds();
        if (!CollectionUtils.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> {
                RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
                roleMenuRelation.setId(CommonUtils.uuid());
                roleMenuRelation.setRoleId(roleMenuRelationVo.getRoleId());
                roleMenuRelation.setMenuId(menuId);
                roleMenuRelationMapper.insert(roleMenuRelation);
            });
        }
        return true;
    }
}