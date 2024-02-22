package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.cache.CacheService;
import org.example.entity.system.vo.ResourceVO;
import org.example.query.ResourceQueryPage;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
public interface ResourceService extends CacheService {
    /**
     * 新增资源
     *
     * @param resourceVo
     * @return
     */
    Boolean addResource(ResourceVO resourceVo);

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    Boolean deleteResource(String id);

    /**
     * 更新资源
     *
     * @param resourceVo
     * @return
     */
    Boolean updateResource(ResourceVO resourceVo);

    /**
     * 分页获取资源列表
     *
     * @param queryPage
     * @return
     */
    Page<ResourceVO> getResourceList(ResourceQueryPage queryPage);

    /**
     * 获取全部资源列表
     *
     * @return
     */
    List<ResourceVO> getAllResourceList();

    /**
     * 根据id获取资源详情
     *
     * @return
     */
    ResourceVO getResourceById(String id);

    /**
     * 根据url和分类id查询资源信息
     *
     * @param url
     * @param categoryId
     * @return
     */
    ResourceVO getResource(String url, String categoryId);

    /**
     * 刷新所有系统中所有资源
     */
    @Async
    void refreshResource();

    /**
     * 根据用户id获取资源列表
     *
     * @param userId
     * @return
     */
    List<ResourceVO> getResourceByUserId(String userId);
}