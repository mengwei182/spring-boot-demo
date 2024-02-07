package org.example.service;

import org.example.cache.CacheService;
import org.example.entity.system.vo.UsernamePasswordVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface BaseService extends CacheService {
    /**
     * 登录
     *
     * @param request
     * @param usernamePasswordVo
     * @return
     */
    String login(HttpServletRequest request, UsernamePasswordVO usernamePasswordVo);

    /**
     * 登出
     *
     * @return
     */
    Boolean logout();

    /**
     * 生成图片验证码
     *
     * @param request
     * @param response
     * @param width 图片宽度
     * @param height 图片高度
     * @param captchaSize 验证码位数
     * @throws IOException
     */
    void generateImageCaptcha(HttpServletRequest request, HttpServletResponse response, Integer width, Integer height, Integer captchaSize) throws IOException;
}