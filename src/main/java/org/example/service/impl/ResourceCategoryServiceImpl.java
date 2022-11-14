package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.common.exception.CommonException;
import org.example.entity.ResourceCategory;
import org.example.entity.vo.ResourceCategoryVo;
import org.example.mapper.ResourceCategoryMapper;
import org.example.service.ResourceCategoryService;
import org.example.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {
    @Resource
    private ResourceCategoryMapper resourceCategoryMapper;

    @Override
    public Boolean addResourceCategory(ResourceCategoryVo resourceCategoryVo) {
        QueryWrapper<ResourceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ResourceCategory::getName, resourceCategoryVo.getName());
        ResourceCategory resourceCategory = resourceCategoryMapper.selectOne(queryWrapper);
        if (resourceCategory != null) {
            throw new CommonException(CommonErrorResult.CATEGORY_EXIST);
        }
        resourceCategory = new ResourceCategory();
        BeanUtils.copyProperties(resourceCategoryVo, resourceCategory);
        resourceCategory.setId(CommonUtils.uuid());
        resourceCategoryMapper.insert(resourceCategory);
        return true;
    }

    @Override
    public Boolean updateResourceCategory(ResourceCategoryVo resourceCategoryVo) {
        ResourceCategory resourceCategory = new ResourceCategory();
        BeanUtils.copyProperties(resourceCategoryVo, resourceCategory);
        resourceCategoryMapper.updateById(resourceCategory);
        return true;
    }
}