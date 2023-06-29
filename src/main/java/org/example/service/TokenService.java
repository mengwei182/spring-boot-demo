package org.example.service;

/**
 * @author 三禾一研发中心后端组
 */
public interface TokenService {
    /**
     * 刷新token
     *
     * @param expiration 过期时间
     * @return
     */
    String refresh(Long expiration);
}