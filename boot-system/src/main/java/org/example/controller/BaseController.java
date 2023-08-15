package org.example.controller;

import org.example.entity.system.vo.UsernamePasswordVo;
import org.example.model.CommonResult;
import org.example.service.BaseService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author lihui
 * @since 2023/4/8
 */
@RestController
@RequestMapping("/base")
public class BaseController {
    @Resource
    private BaseService baseService;

    /**
     * 登录
     *
     * @param usernamePasswordVo
     * @return
     */
    @RequestMapping("/login")
    public CommonResult<String> login(@Valid @RequestBody UsernamePasswordVo usernamePasswordVo) {
        return CommonResult.success(baseService.login(usernamePasswordVo));
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping("/logout")
    public CommonResult<Boolean> logout() {
        return CommonResult.success(baseService.logout());
    }

    /**
     * 获取图片验证码
     *
     * @param response
     * @param width 图片宽度
     * @param height 图片高度
     * @param captchaSize 验证码位数
     * @throws IOException
     */
    @RequestMapping("/image/captcha")
    public void getImageCaptcha(HttpServletResponse response, @RequestParam(defaultValue = "130") Integer width, @RequestParam(defaultValue = "30") Integer height, @RequestParam(defaultValue = "4") Integer captchaSize) throws IOException {
        baseService.generateImageCaptcha(response, width, height, captchaSize);
    }
}