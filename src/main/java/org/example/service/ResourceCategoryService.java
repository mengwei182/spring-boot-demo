package org.example.service;

import org.example.common.model.CommonResult;
import org.example.entity.ResourceCategory;

public interface ResourceCategoryService {
    CommonResult addResourceCategory(ResourceCategory resourceCategory);

    CommonResult updateResourceCategory(ResourceCategory resourceCategory);
}