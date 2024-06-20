package org.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.common.core.exception.ExceptionInformation;
import org.example.common.core.util.CommonUtils;
import org.example.common.core.util.PageUtils;
import org.example.system.entity.Resource;
import org.example.system.entity.ResourceCategory;
import org.example.system.entity.vo.ResourceCategoryVO;
import org.example.system.entity.vo.ResourceVO;
import org.example.system.exception.SystemException;
import org.example.system.mapper.ResourceCategoryMapper;
import org.example.system.mapper.ResourceMapper;
import org.example.system.entity.query.ResourceCategoryQueryPage;
import org.example.system.service.ResourceCategoryService;
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
    public ResourceCategoryVO addResourceCategory(ResourceCategoryVO resourceCategoryVo) {
        QueryWrapper<ResourceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ResourceCategory::getName, resourceCategoryVo.getName());
        ResourceCategory resourceCategory = resourceCategoryMapper.selectOne(queryWrapper);
        if (resourceCategory != null) {
            throw new SystemException(ExceptionInformation.SYSTEM_3004.getCode(), ExceptionInformation.SYSTEM_3004.getMessage());
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
            throw new SystemException(ExceptionInformation.SYSTEM_3003.getCode(), ExceptionInformation.SYSTEM_3003.getMessage());
        }
        Long count = resourceMapper.selectCount(new LambdaQueryWrapper<Resource>().eq(Resource::getCategoryId, id));
        if (count != null && count > 0) {
            throw new SystemException(ExceptionInformation.SYSTEM_3006.getCode(), ExceptionInformation.SYSTEM_3006.getMessage());
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
    public Boolean updateResourceCategory(ResourceCategoryVO resourceCategoryVo) {
        QueryWrapper<ResourceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ResourceCategory::getName, resourceCategoryVo.getName());
        ResourceCategory resourceCategory = resourceCategoryMapper.selectOne(queryWrapper);
        if (resourceCategory != null) {
            throw new SystemException(ExceptionInformation.SYSTEM_3004.getCode(), ExceptionInformation.SYSTEM_3004.getMessage());
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
    public Page<ResourceCategoryVO> getResourceCategoryList(ResourceCategoryQueryPage queryPage) {
        Page<ResourceCategory> page = new Page<>(queryPage.getPageNumber(), queryPage.getPageSize());
        List<ResourceCategory> resourceCategories = resourceCategoryMapper.getResourceCategoryList(page, queryPage);
        page.setRecords(resourceCategories);
        Page<ResourceCategoryVO> resltPage = PageUtils.wrap(page, ResourceCategoryVO.class);
        List<ResourceCategoryVO> records = resltPage.getRecords();
        for (ResourceCategoryVO record : records) {
            List<Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<Resource>().eq(Resource::getCategoryId, record.getId()));
            record.setResources(CommonUtils.transformList(resources, ResourceVO.class));
        }
        return resltPage;
    }

    /**
     * 获取全部资源分类列表
     *
     * @return
     */
    @Override
    public List<ResourceCategoryVO> getAllResourceCategoryList() {
        List<ResourceCategory> resourceCategories = resourceCategoryMapper.selectList(new LambdaQueryWrapper<>());
        List<ResourceCategoryVO> resourceCategoryVOS = CommonUtils.transformList(resourceCategories, ResourceCategoryVO.class);
        for (ResourceCategoryVO resourceCategoryVo : resourceCategoryVOS) {
            List<Resource> resources = resourceMapper.selectList(new LambdaQueryWrapper<Resource>().eq(Resource::getCategoryId, resourceCategoryVo.getId()));
            resourceCategoryVo.setResources(CommonUtils.transformList(resources, ResourceVO.class));
        }
        return resourceCategoryVOS;
    }

    /**
     * 根据资源分类名称查询资源分类信息
     *
     * @param name
     * @return
     */
    @Override
    public ResourceCategoryVO getResourceCategoryByName(String name) {
        ResourceCategory resourceCategory = resourceCategoryMapper.selectOne(new LambdaQueryWrapper<ResourceCategory>().eq(ResourceCategory::getName, name));
        return CommonUtils.transformObject(resourceCategory, ResourceCategoryVO.class);
    }
}