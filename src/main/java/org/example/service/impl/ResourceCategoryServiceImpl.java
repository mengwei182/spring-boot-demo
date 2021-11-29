package org.example.service.impl;

import org.example.common.model.CommonResult;
import org.example.common.util.CommonUtils;
import org.example.entity.ResourceCategory;
import org.example.mapper.ResourceCategoryMapper;
import org.example.service.ResourceCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {
    @Resource
    private ResourceCategoryMapper resourceCategoryMapper;

    @Override
    public CommonResult addResourceCategory(ResourceCategory resourceCategory) {
        resourceCategory.setId(CommonUtils.getUUID());
        resourceCategoryMapper.addResourceCategory(resourceCategory);
        return CommonResult.success();
    }

    @Override
    public CommonResult updateResourceCategory(ResourceCategory resourceCategory) {
        resourceCategoryMapper.updateResourceCategory(resourceCategory);
        return CommonResult.success();
    }
}