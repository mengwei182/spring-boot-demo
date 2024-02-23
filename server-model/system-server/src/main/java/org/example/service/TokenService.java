package org.example.service;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface TokenService {
    /**
     * 刷新token
     *
     * @param userId 用户id
     * @return
     */
    String refresh(String userId);

    /**
     * 清理用户token缓存
     *
     * @param userId
     */
    void clearTokenCache(String userId);
}