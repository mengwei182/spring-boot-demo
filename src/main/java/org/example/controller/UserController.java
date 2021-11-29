package org.example.controller;

import org.example.api.UserQueryPage;
import org.example.common.model.CommonResult;
import org.example.entity.User;
import org.example.entity.UserRoleRelation;
import org.example.service.UserService;
import org.example.vo.UsernamePasswordVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CommonResult login(@RequestBody UsernamePasswordVo usernamePasswordVo) {
        return userService.login(usernamePasswordVo);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CommonResult register(@RequestBody User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/phone/verify/code", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public CommonResult getPhoneVerifyCode(@RequestParam String phone) {
        return userService.getPhoneVerifyCode(phone);
    }

    @RequestMapping(value = "/image/verify/code", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public void getImageVerifyCode(HttpServletResponse response, @RequestParam String account) throws IOException {
        userService.getImageVerifyCode(response, account);
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public CommonResult getUserList(@ModelAttribute UserQueryPage queryPage) {
        return userService.getUserList(queryPage);
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public CommonResult getUserInfo(Principal principal) {
        return userService.getUserInfo(principal);
    }

    @RequestMapping(value = "/user/info/id", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public CommonResult getUserInfoById(@RequestParam String userId) {
        return userService.getUserInfoById(userId);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public CommonResult updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/user/password/update", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public CommonResult updateUserPassword(@Valid @RequestBody UsernamePasswordVo usernamePasswordVo) {
        return userService.updateUserPassword(usernamePasswordVo);
    }

    @RequestMapping(value = "/user/role/update", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public CommonResult updateUserRole(@RequestBody UserRoleRelation userRoleRelation) {
        return userService.updateUserRole(userRoleRelation);
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
    public CommonResult deleteUser(@RequestParam String userId) {
        return userService.deleteUser(userId);
    }
}