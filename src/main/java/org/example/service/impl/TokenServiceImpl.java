package org.example.service.impl;

import org.example.entity.vo.TokenVo;
import org.example.entity.vo.UserInfoVo;
import org.example.error.SystemServerResult;
import org.example.error.exception.CommonException;
import org.example.service.TokenService;
import org.example.usercontext.UserContext;
import org.example.util.TokenUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 三禾一研发中心后端组
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 刷新token
     *
     * @param expiration 过期时间
     * @return
     */
    @Override
    public String refresh(Long expiration) {
        Date date = new Date();
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.setTime(date);
        minCalendar.add(Calendar.DATE, 1);
        long minTime = minCalendar.getTime().getTime();
        if (expiration < minTime) {
            throw new CommonException(SystemServerResult.TOKEN_EXPIRATION_TIMEOUT_MIN);
        }
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.setTime(date);
        maxCalendar.add(Calendar.YEAR, 3);
        long maxExpiration = maxCalendar.getTime().getTime();
        if (expiration > maxExpiration) {
            throw new CommonException(SystemServerResult.TOKEN_EXPIRATION_TIMEOUT_MAX);
        }
        String userId = UserContext.get().getUserId();
        UserInfoVo userInfoVo = UserContext.get().getUserInfoVo();
        // 删除已存储的用户token
        redisTemplate.delete(userId);
        userInfoVo.setLoginTime(date);
        TokenVo<?> tokenVo = new TokenVo<>(userId, date, userInfoVo);
        String token = TokenUtils.sign(tokenVo);
        // 重新设置token
        redisTemplate.opsForValue().set(userId, token, 60 * 60, TimeUnit.SECONDS);
        return token;
    }
}