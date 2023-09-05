package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.ResourceCategoryQueryPage;
import org.example.entity.system.Resource;
import org.example.entity.system.ResourceCategory;
import org.example.entity.system.vo.ResourceCategoryVo;
import org.example.entity.system.vo.ResourceVo;
import org.example.error.SystemServerResult;
import org.example.error.exception.CommonException;
import org.example.mapper.ResourceCategoryMapper;
import org.example.mapper.ResourceMapper;
import org.example.service.ResourceCategoryService;
import org.example.util.CommonUtils;
import org.example.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    @javax.annotation.Resource
    private ResourceCategoryMapper resourceCategoryMapper;

    /**
     * 新增资源分类信息
     *
     * @param resourceCategoryVo
     * @return
     */
    @Override
    public ResourceCategoryVo addResourceCategory(ResourceCategoryVo resourceCategoryVo) {
        QueryWrapper<ResourceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ResourceCategory::getName, resourceCategoryVo.getName());
        ResourceCategory resourceCategory = resourceCategoryMapper.selectOne(queryWrapper);
        if (resourceCategory != null) {
            throw new CommonException(SystemServerResult.CATEGORY_EXIST);
        }
        resourceCategory = new ResourceCategory();
        BeanUtils.copyProperties(resourceCategoryVo, resourceCategory);
        resourceCategory.setId(CommonUtils.uuid());
        resourceCategoryMapper.insert(resourceCategory);
        resourceCategoryVo.setId(resourceCategory.getId());
        return resourceCategoryVo;
    }

    /**
     * 删除资源分类信息
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteResourceCategory(String id) {
        ResourceCategory resourceCategory = resourceCategoryMapper.selectById(id);
        if (resourceCategory == null) {
            throw new CommonException(SystemServerResult.CATEGORY_NOT_EXIST);
        }
        Long count = resourceMapper.selectCount(new LambdaQueryWrapper<Resource>().eq(Resource::getCategoryId, id));
        if (count != null && count > 0) {
            throw new CommonException(SystemServerResult.CATEGORY_RESOURCE_EXIST);
        }
        resourceCategoryMapper.deleteById(id);
        return true;
    }

    /**
     * 更新资源分类
     *
     * @param resourceCategoryVo
     * @return
     */
    @Override
    public Boolean updateResourceCategory(ResourceCategoryVo resourceCategoryVo) {
        QueryWrapper<ResourceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ResourceCategory::getName, resourceCategoryVo.getName());
        ResourceCategory resourceCategory = resourceCategoryMapper.selectOne(queryWrapper);
        if (resourceCategory != null) {
            throw new CommonException(SystemServerResult.CATEGORY_EXIST);
        }
        resourceCategory = new ResourceCategory();
        BeanUtils.copyProperties(resourceCategoryVo, resourceCategory);
        resourceCategoryMapper.updateById(resourceCategory);
        return true;
    }

    /**
     * 获取资源分类列表
     *
     * @param queryPage
     * @return
     */
    @Override
    public Page<ResourceCategoryVo> getResourceCategoryList(ResourceCategoryQueryPage queryPage) {
        Page<ResourceCategory> page = new Page<>(queryPage.getPageNumber(), queryPage.getPageSize());
        List<ResourceCategory> resourceCategories = resourceCategoryMapper.getResourceCategoryList(page, queryPage);
        page.setRecords(resourceCategories);
        Page<ResourceCategoryVo> resltPage = PageUtils.wrap(page, ResourceCategoryVo.class);
        List<ResourceCategoryVo> records = resltPage.getRecords();
        for (ResourceCategoryVo record : records) {
            List<Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<Resource>().eq(Resource::getCategoryId, record.getId()));
            record.setResources(CommonUtils.transformList(resources, ResourceVo.class));
        }
        return resltPage;
    }

    /**
     * 获取全部资源分类列表
     *
     * @return
     */
    @Override
    public List<ResourceCategoryVo> getAllResourceCategoryList() {
        List<ResourceCategory> resourceCategories = resourceCategoryMapper.selectList(new LambdaQueryWrapper<>());
        List<ResourceCategoryVo> resourceCategoryVos = CommonUtils.transformList(resourceCategories, ResourceCategoryVo.class);
        for (ResourceCategoryVo resourceCategoryVo : resourceCategoryVos) {
            List<Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<Resource>().eq(Resource::getCategoryId, resourceCategoryVo.getId()));
            resourceCategoryVo.setResources(CommonUtils.transformList(resources, ResourceVo.class));
        }
        return resourceCategoryVos;
    }

    /**
     * 根据资源分类名称查询资源分类信息
     *
     * @param name
     * @return
     */
    @Override
    public ResourceCategoryVo getResourceCategoryByName(String name) {
        ResourceCategory resourceCategory = resourceCategoryMapper.selectOne(new LambdaQueryWrapper<ResourceCategory>().eq(ResourceCategory::getName, name));
        return CommonUtils.transformObject(resourceCategory, ResourceCategoryVo.class);
    }
}