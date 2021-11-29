package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.RoleMenuRelation;

import java.util.List;

@Mapper
public interface RoleMenuRelationMapper {
    void addRoleMenuRelation(RoleMenuRelation roleMenuRelation);

    List<RoleMenuRelation> getRoleMenuRelations(String userId);

    void deleteRoleMenuRelationByRoleId(String userId);

    void deleteRoleMenuRelationByMenuId(String menuId);
}