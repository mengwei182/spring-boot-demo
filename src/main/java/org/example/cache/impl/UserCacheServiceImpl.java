package org.example.cache.impl;

import org.example.cache.UserCacheService;
import org.example.entity.User;
import org.example.redis.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserCacheServiceImpl implements UserCacheService {
    @Resource
    private RedisService redisService;
    private static final String USER_CACHE_PREFIX = "USER_CACHE_PREFIX_";
    private static final String USER_CACHE_PHONE_VERIFY_PREFIX = "USER_CACHE_PHONE_VERIFY_PREFIX_";
    private static final String USER_CACHE_IMAGE_VERIFY_PREFIX = "USER_CACHE_IMAGE_VERIFY_PREFIX_";

    @Override
    public void setUser(User user) {
        redisService.set(USER_CACHE_PREFIX.concat(user.getId()), user);
        redisService.set(USER_CACHE_PREFIX.concat(user.getUsername()), user);
    }

    @Override
    public User getUserBuUserId(String userId) {
        return (User) redisService.get(USER_CACHE_PREFIX.concat(userId));
    }

    @Override
    public void deleteUserByUserId(String userId) {
        redisService.remove(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return (User) redisService.get(USER_CACHE_PREFIX.concat(username));
    }

    @Override
    public void setPhoneVerifyCode(String phone, String verifyCode) {
        redisService.set(USER_CACHE_PHONE_VERIFY_PREFIX.concat(phone), verifyCode);

    }

    @Override
    public String getPhoneVerifyCode(String phone) {
        return (String) redisService.get(USER_CACHE_PHONE_VERIFY_PREFIX.concat(phone));
    }

    @Override
    public void deletePhoneVerifyCode(String phone) {
        redisService.remove(USER_CACHE_PHONE_VERIFY_PREFIX.concat(phone));
    }

    @Override
    public void setImageVerifyCode(String account, String verifyCode) {
        redisService.set(USER_CACHE_IMAGE_VERIFY_PREFIX.concat(account), verifyCode);
    }

    @Override
    public String getImageVerifyCode(String account) {
        return (String) redisService.get(USER_CACHE_IMAGE_VERIFY_PREFIX.concat(account));
    }

    @Override
    public void deleteImageVerifyCode(String account) {
        redisService.remove(USER_CACHE_IMAGE_VERIFY_PREFIX.concat(account));
    }
}