package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.ResourceCategory;

@Mapper
public interface ResourceCategoryMapper {
    void addResourceCategory(ResourceCategory resourceCategory);

    void updateResourceCategory(ResourceCategory resourceCategory);
}