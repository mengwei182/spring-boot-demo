package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.common.model.QueryPage;
import org.example.entity.User;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    void addUser(User user);

    User getUserByUserId(String id);

    List<User> getUserList(QueryPage queryPage);

    Integer getUserListCount(QueryPage queryPage);

    User getUserByUsername(String username);

    void updateUser(User user);

    void updatePassword(@Param("id") String id, @Param("password") String password);

    void updateEmail(@Param("id") String id, @Param("email") String email);

    void deleteUserByUserId(String userId);

    void updateLoginTime(String id, Date updateDate);
}