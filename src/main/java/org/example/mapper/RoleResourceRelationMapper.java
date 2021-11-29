package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.RoleResourceRelation;

import java.util.List;

@Mapper
public interface RoleResourceRelationMapper {
    void addRoleResourceRelation(RoleResourceRelation roleResourceRelation);

    List<RoleResourceRelation> getRoleResourceRelations(String userId);

    List<RoleResourceRelation> getAllRoleResourceRelationList();
}