package org.example.service.impl;

import org.example.common.global.I18nMessage;
import org.example.common.model.CommonResult;
import org.example.common.util.CommonUtils;
import org.example.entity.Role;
import org.example.entity.RoleMenuRelation;
import org.example.mapper.RoleMapper;
import org.example.mapper.RoleMenuRelationMapper;
import org.example.mapper.UserRoleRelationMapper;
import org.example.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;
    @Resource
    private RoleMenuRelationMapper roleMenuRelationMapper;

    @Override
    public CommonResult addRole(Role role) {
        role.setId(CommonUtils.getUUID());
        roleMapper.addRole(role);
        return CommonResult.success();
    }

    @Override
    public CommonResult deleteRole(String id) {
        roleMapper.deleteRole(id);
        userRoleRelationMapper.deleteUserRoleRelationByRoleId(id);
        roleMenuRelationMapper.deleteRoleMenuRelationByRoleId(id);
        return CommonResult.success();
    }

    @Override
    public CommonResult updateRole(Role role) {
        roleMapper.updateRole(role);
        return CommonResult.success();
    }

    @Override
    public CommonResult updateRoleMenu(RoleMenuRelation roleMenuRelation) {
        if (roleMapper.getRoleByRoleId(roleMenuRelation.getRoleId()) == null) {
            return CommonResult.failed(I18nMessage.PERMISSION_VERIFICATION_FAILED);
        }
        roleMenuRelationMapper.deleteRoleMenuRelationByRoleId(roleMenuRelation.getRoleId());
        List<String> menuIds = roleMenuRelation.getMenuIds();
        if (CollectionUtils.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> {
                RoleMenuRelation rmr = new RoleMenuRelation();
                rmr.setId(CommonUtils.getUUID());
                rmr.setRoleId(roleMenuRelation.getRoleId());
                rmr.setMenuId(menuId);
                roleMenuRelationMapper.addRoleMenuRelation(rmr);
            });
        }
        return CommonResult.success();
    }
}