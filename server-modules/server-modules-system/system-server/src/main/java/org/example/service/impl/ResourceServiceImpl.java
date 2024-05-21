package org.example.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.CaffeineRedisCache;
import org.example.mapper.ResourceCategoryMapper;
import org.example.mapper.ResourceMapper;
import org.example.mapper.RoleResourceRelationMapper;
import org.example.mapper.UserRoleRelationMapper;
import org.example.common.core.result.SystemServerResult;
import org.example.common.core.result.exception.SystemException;
import org.example.service.ResourceCategoryService;
import org.example.service.ResourceService;
import org.example.system.entity.Resource;
import org.example.system.entity.ResourceCategory;
import org.example.system.entity.RoleResourceRelation;
import org.example.system.entity.UserRoleRelation;
import org.example.system.entity.vo.ResourceCategoryVO;
import org.example.system.entity.vo.ResourceVO;
import org.example.system.query.ResourceQueryPage;
import org.example.common.core.util.CommonUtils;
import org.example.common.core.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    @Value("${spring.application.name}")
    private String applicationName;
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    @javax.annotation.Resource
    private ResourceCategoryMapper resourceCategoryMapper;
    @javax.annotation.Resource
    private ResourceCategoryService resourceCategoryService;
    @javax.annotation.Resource
    private RoleResourceRelationMapper roleResourceRelationMapper;
    @javax.annotation.Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @javax.annotation.Resource
    private UserRoleRelationMapper userRoleRelationMapper;
    @javax.annotation.Resource
    private CaffeineRedisCache caffeineRedisCache;

    /**
     * 新增资源
     *
     * @param resourceVo
     * @return
     */
    @Override
    public Boolean addResource(ResourceVO resourceVo) {
        ResourceCategory resourceCategory = resourceCategoryMapper.selectById(resourceVo.getCategoryId());
        if (resourceCategory == null) {
            throw new SystemException(SystemServerResult.CATEGORY_NOT_EXIST);
        }
        Long count = resourceMapper.selectCount(new LambdaQueryWrapper<Resource>().eq(Resource::getCategoryId, resourceVo.getCategoryId()).eq(Resource::getName, resourceVo.getName()));
        if (count != null && count > 0) {
            throw new SystemException(SystemServerResult.RESOURCE_NAME_DUPLICATE);
        }
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceVo, resource);
        resource.setId(CommonUtils.uuid());
        resourceMapper.insert(resource);
        return true;
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteResource(String id) {
        Resource resource = resourceMapper.selectById(id);
        if (resource == null) {
            throw new SystemException(SystemServerResult.RESOURCE_NOT_EXIST);
        }
        // 删除角色资源表的关联信息
        roleResourceRelationMapper.delete(new LambdaQueryWrapper<RoleResourceRelation>().eq(RoleResourceRelation::getResourceId, id));
        resourceMapper.deleteById(id);
        return true;
    }

    /**
     * 更新资源
     *
     * @param resourceVo
     * @return
     */
    @Override
    public Boolean updateResource(ResourceVO resourceVo) {
        Resource resource = resourceMapper.selectById(resourceVo.getId());
        if (resource == null) {
            throw new SystemException(SystemServerResult.RESOURCE_NOT_EXIST);
        }
        ResourceCategory resourceCategory = resourceCategoryMapper.selectById(resourceVo.getCategoryId());
        if (resourceCategory == null) {
            throw new SystemException(SystemServerResult.CATEGORY_NOT_EXIST);
        }
        Long count = resourceMapper.selectCount(new LambdaQueryWrapper<Resource>().eq(Resource::getCategoryId, resourceVo.getCategoryId()).eq(Resource::getName, resourceVo.getName()));
        if (count != null && count > 0) {
            throw new SystemException(SystemServerResult.RESOURCE_NAME_DUPLICATE);
        }
        Resource insterResource = new Resource();
        BeanUtils.copyProperties(resourceVo, insterResource);
        resourceMapper.updateById(insterResource);
        return true;
    }

    /**
     * 分页获取资源列表
     *
     * @param queryPage
     * @return
     */
    @Override
    public Page<ResourceVO> getResourceList(ResourceQueryPage queryPage) {
        Page<Resource> page = new Page<>();
        List<Resource> resourceList = resourceMapper.getResourceList(page, queryPage);
        page.setRecords(resourceList);
        return PageUtils.wrap(page, ResourceVO.class);
    }

    /**
     * 获取全部资源列表
     *
     * @return
     */
    @Override
    public List<ResourceVO> getAllResourceList() {
        List<Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<>());
        return CommonUtils.transformList(resources, ResourceVO.class);
    }

    /**
     * 根据id获取资源详情
     *
     * @return
     */
    @Override
    public ResourceVO getResourceById(String id) {
        Resource resource = resourceMapper.selectById(id);
        ResourceVO resourceVo = new ResourceVO();
        BeanUtils.copyProperties(resource, resourceVo);
        return resourceVo;
    }

    /**
     * 根据url和分类id查询资源信息
     *
     * @param url
     * @param categoryId
     * @return
     */
    @Override
    public ResourceVO getResource(String url, String categoryId) {
        Resource resource = resourceMapper.selectOne(new LambdaQueryWrapper<Resource>().eq(Resource::getUrl, url).eq(Resource::getCategoryId, categoryId));
        return CommonUtils.transformObject(resource, ResourceVO.class);
    }

    /**
     * 刷新所有系统中所有资源
     */
    @Override
    public void refreshResource() {
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = map.keySet();
        for (RequestMappingInfo requestMappingInfo : requestMappingInfos) {
            // controller类名称
            HandlerMethod handlerMethod = map.get(requestMappingInfo);
            // controller类全限定名
            String name = handlerMethod.getBeanType().getName();
            // 请求地址
            PathPatternsRequestCondition pathPatternsCondition = requestMappingInfo.getPathPatternsCondition();
            if (pathPatternsCondition == null) {
                continue;
            }
            String categoryName = applicationName + "_" + name;
            String categoryId = CommonUtils.uuid();
            ResourceCategoryVO resourceCategoryVo = resourceCategoryService.getResourceCategoryByName(categoryName);
            // 资源分类不存在
            if (resourceCategoryVo == null) {
                resourceCategoryVo = new ResourceCategoryVO();
                resourceCategoryVo.setId(categoryId);
                resourceCategoryVo.setName(categoryName);
                resourceCategoryVo = resourceCategoryService.addResourceCategory(resourceCategoryVo);
                log.info("add resource category:{}", resourceCategoryVo.getName());
            }
            categoryId = resourceCategoryVo.getId();
            Set<PathPattern> patterns = pathPatternsCondition.getPatterns();
            for (PathPattern pattern : patterns) {
                ResourceVO resourceVo = getResource(pattern.getPatternString(), categoryId);
                // 资源已存在，直接跳过
                if (resourceVo != null) {
                    continue;
                }
                resourceVo = new ResourceVO();
                resourceVo.setName(handlerMethod.getMethod().getName());
                resourceVo.setCategoryId(categoryId);
                resourceVo.setUrl(pattern.getPatternString());
                addResource(resourceVo);
                log.info("add resource:{}", resourceVo.getName());
            }
        }
    }

    /**
     * 根据用户id获取资源列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ResourceVO> getResourceByUserId(String userId) {
        String key = SystemServerResult.RESOURCE_KEY + userId;
        List<ResourceVO> resourceVOS = (List<ResourceVO>) caffeineRedisCache.get(key, List.class);
        if (CollectionUtil.isEmpty(resourceVOS)) {
            List<UserRoleRelation> userRoleRelations = userRoleRelationMapper.selectList(new LambdaQueryWrapper<UserRoleRelation>().eq(UserRoleRelation::getUserId, userId));
            List<RoleResourceRelation> roleResourceRelations = roleResourceRelationMapper.selectList(new LambdaQueryWrapper<RoleResourceRelation>().in(RoleResourceRelation::getRoleId, userRoleRelations.stream().map(UserRoleRelation::getRoleId).collect(Collectors.toList())));
            List<Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<Resource>().in(Resource::getId, roleResourceRelations.stream().map(RoleResourceRelation::getResourceId).collect(Collectors.toList())));
            resourceVOS = CommonUtils.transformList(resources, ResourceVO.class);
        }
        caffeineRedisCache.put(key, resourceVOS, Duration.ofHours(1));
        return resourceVOS;
    }

    /**
     * 清理用户资源缓存
     *
     * @param userId
     */
    @Override
    public void clearResourceCache(String userId) {
        caffeineRedisCache.evict(SystemServerResult.RESOURCE_KEY + userId);
    }
}