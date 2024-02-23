package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.system.entity.User;
import org.example.system.query.UserQueryPage;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getUserList(IPage<User> page, @Param("queryPage") UserQueryPage queryPage);
}