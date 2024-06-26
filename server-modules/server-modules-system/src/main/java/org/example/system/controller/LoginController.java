package org.example.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.core.result.CommonResult;
import org.example.system.entity.UserLoginVO;
import org.example.system.entity.vo.TokenVO;
import org.example.system.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author lihui
 * @since 2023/4/8
 */
@Api(tags = "登录")
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 登录
     *
     * @param userLoginVO
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public CommonResult<TokenVO> login(@Valid @RequestBody UserLoginVO userLoginVO) {
        return CommonResult.success(loginService.login(userLoginVO));
    }

    /**
     * 登出
     *
     * @return
     */
    @ApiOperation("登出")
    @GetMapping("/logout")
    public CommonResult<Boolean> logout() {
        return CommonResult.success(loginService.logout());
    }

    /**
     * 获取图片验证码
     *
     * @param width 图片宽度
     * @param height 图片高度
     * @param captchaSize 验证码位数
     * @throws IOException
     */
    @ApiOperation("获取图片验证码")
    @GetMapping("/login/image/captcha")
    public void getImageCaptcha(@RequestParam(defaultValue = "130") Integer width, @RequestParam(defaultValue = "30") Integer height, @RequestParam(defaultValue = "4") Integer captchaSize) throws IOException {
        loginService.generateImageCaptcha(width, height, captchaSize);
    }
}