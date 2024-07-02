package org.example.system.service;


import org.example.system.entity.UserLoginVO;
import org.example.system.entity.vo.TokenVO;

import java.io.IOException;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface LoginService {
    /**
     * 登录
     *
     * @param userLoginVO
     * @return
     */
    TokenVO login(UserLoginVO userLoginVO);

    /**
     * 登出
     *
     * @return
     */
    Boolean logout();

    /**
     * 生成图片验证码
     *
     * @param width 图片宽度
     * @param height 图片高度
     * @param captchaSize 验证码位数
     * @throws IOException
     */
    void generateImageCaptcha(Integer width, Integer height, Integer captchaSize) throws IOException;
}