package org.example.system.service;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface TokenService {
    /**
     * 刷新token
     *
     * @return
     */
    String refresh();

    /**
     * 清理用户token缓存
     *
     * @param userId
     */
    void clearTokenCache(String userId);
}