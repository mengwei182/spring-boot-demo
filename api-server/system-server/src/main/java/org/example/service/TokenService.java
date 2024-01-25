package org.example.service;

import org.example.cache.CacheService;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface TokenService extends CacheService {
    /**
     * 刷新token
     *
     * @param userId 用户id
     * @return
     */
    String refresh(String userId);
}