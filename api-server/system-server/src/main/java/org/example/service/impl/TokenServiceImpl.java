package org.example.service.impl;

import org.example.CaffeineRedisCache;
import org.example.entity.base.Token;
import org.example.entity.system.vo.UserVo;
import org.example.result.SystemServerResult;
import org.example.result.exception.SystemException;
import org.example.service.TokenService;
import org.example.service.UserService;
import org.example.cache.CacheService;
import org.example.util.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private UserService userService;
    @Resource
    private CaffeineRedisCache caffeineRedisCache;

    /**
     * 刷新token
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public String refresh(String userId) {
        UserVo userVo = userService.getUserInfo(userId);
        if (userVo == null) {
            throw new SystemException(SystemServerResult.USER_NOT_EXIST);
        }
        long time = userService.getTokenExpireTime(userId);
        // token已经过期
        if (time <= 0) {
            // 删除token缓存
            clear(userId);
            throw new SystemException(SystemServerResult.TOKEN_EXPIRATION_TIME_INVALID);
        }
        Token<UserVo> token = new Token<>(userId, new Date(), userVo);
        // 重新设置token
        caffeineRedisCache.put(SystemServerResult.USER_TOKEN_KEY + userId, token, Duration.ofMillis(time));
        return TokenUtils.sign(token);
    }

    @Override
    public void clear(Object... ids) {
        caffeineRedisCache.evict(SystemServerResult.USER_TOKEN_KEY + ids[0]);
    }
}