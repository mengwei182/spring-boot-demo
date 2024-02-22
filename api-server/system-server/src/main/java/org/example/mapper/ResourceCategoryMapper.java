package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.system.ResourceCategory;
import org.example.query.ResourceCategoryQueryPage;

import java.util.List;

@Mapper
public interface ResourceCategoryMapper extends BaseMapper<ResourceCategory> {
    List<ResourceCategory> getResourceCategoryList(Page<ResourceCategory> page, @Param("queryPage") ResourceCategoryQueryPage queryPage);
}