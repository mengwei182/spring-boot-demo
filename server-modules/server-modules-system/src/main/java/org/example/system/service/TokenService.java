package org.example.system.service;

import org.example.system.entity.vo.TokenVO;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface TokenService {
    /**
     * 刷新token
     *
     * @param refreshToken
     * @return
     */
    TokenVO refresh(String refreshToken);

    /**
     * 清理用户token缓存
     *
     * @param userId
     */
    void clearTokenCache(Long userId);
}