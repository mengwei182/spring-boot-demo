package org.example.service;

import org.example.common.model.CommonResult;
import org.example.common.model.QueryPage;
import org.example.entity.User;
import org.example.entity.UserRoleRelation;
import org.example.vo.UsernamePasswordVo;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public interface UserService extends UserDetailsService {
    CommonResult login(UsernamePasswordVo usernamePasswordVo);

    CommonResult logout(HttpServletRequest request);

    CommonResult register(User user);

    CommonResult getPhoneVerifyCode(String phone);

    void getImageVerifyCode(HttpServletResponse response, String account) throws IOException;

    CommonResult getUserInfo(Principal principal);

    CommonResult getUserInfoById(String userId);

    CommonResult getUserList(QueryPage queryPage);

    CommonResult updateUser(User user);

    CommonResult updateUserPassword(UsernamePasswordVo usernamePasswordVo);

    CommonResult updateUserRole(UserRoleRelation userRoleRelation);

    CommonResult deleteUser(String userId);
}