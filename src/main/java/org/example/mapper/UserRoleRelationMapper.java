package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.UserRoleRelation;

import java.util.List;

@Mapper
public interface UserRoleRelationMapper {
    void addUserRoleRelation(UserRoleRelation userRoleRelation);

    List<UserRoleRelation> getUserRoleRelations(String userId);

    void deleteUserRoleRelationByRoleId(String roleId);

    void deleteUserRoleRelationByUserId(String userId);
}