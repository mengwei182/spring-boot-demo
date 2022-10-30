package org.example.cache.impl;

import org.example.cache.UserCacheService;
import org.example.redis.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserCacheServiceImpl implements UserCacheService {
    @Resource
    private RedisService redisService;
    private static final String USER_CACHE_PREFIX = "USER_CACHE_PREFIX_";
    private static final String USER_CACHE_PHONE_VERIFY_PREFIX = "USER_CACHE_PHONE_VERIFY_PREFIX_";
    private static final String USER_CACHE_IMAGE_VERIFY_PREFIX = "USER_CACHE_IMAGE_VERIFY_PREFIX_";

    @Override
    public void setPhoneVerifyCode(String phone, String verifyCode, Long timeout) {
        if (timeout != null && timeout > 0) {
            redisService.getValueOperations().set(USER_CACHE_PHONE_VERIFY_PREFIX.concat(phone), verifyCode, timeout, TimeUnit.MINUTES);
        } else {
            redisService.getValueOperations().set(USER_CACHE_PHONE_VERIFY_PREFIX.concat(phone), verifyCode);
        }
    }

    @Override
    public String getPhoneVerifyCode(String phone) {
        return (String) redisService.getValueOperations().get(USER_CACHE_PHONE_VERIFY_PREFIX.concat(phone));
    }

    @Override
    public void deletePhoneVerifyCode(String phone) {
        redisService.remove(USER_CACHE_PHONE_VERIFY_PREFIX.concat(phone));
    }

    @Override
    public void setImageVerifyCode(String account, String verifyCode, Long timeout) {
        if (timeout != null && timeout > 0) {
            redisService.getValueOperations().set(USER_CACHE_IMAGE_VERIFY_PREFIX.concat(account), verifyCode, timeout, TimeUnit.MINUTES);
        } else {
            redisService.getValueOperations().set(USER_CACHE_IMAGE_VERIFY_PREFIX.concat(account), verifyCode);
        }
    }

    @Override
    public String getImageVerifyCode(String account) {
        return (String) redisService.getValueOperations().get(USER_CACHE_IMAGE_VERIFY_PREFIX.concat(account));
    }

    @Override
    public void deleteImageVerifyCode(String account) {
        redisService.remove(USER_CACHE_IMAGE_VERIFY_PREFIX.concat(account));
    }
}