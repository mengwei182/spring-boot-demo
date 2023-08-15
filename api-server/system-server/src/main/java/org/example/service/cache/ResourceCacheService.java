package org.example.service.cache;

import org.example.entity.system.vo.ResourceVo;

import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface ResourceCacheService {
    /**
     * 根据用户id获取资源列表
     *
     * @param userId
     * @return
     */
    List<ResourceVo> getResourceByUserId(String userId);
}