package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.UserRoleRelation;

@Mapper
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {
}