package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.system.Role;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}