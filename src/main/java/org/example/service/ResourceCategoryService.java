package org.example.service;

import org.example.entity.vo.ResourceCategoryVo;

public interface ResourceCategoryService {
    Boolean addResourceCategory(ResourceCategoryVo resourceCategoryVo);

    Boolean updateResourceCategory(ResourceCategoryVo resourceCategoryVo);
}