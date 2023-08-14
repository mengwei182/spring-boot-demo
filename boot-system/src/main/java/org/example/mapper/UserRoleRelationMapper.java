package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.system.UserRoleRelation;

@Mapper
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {
}