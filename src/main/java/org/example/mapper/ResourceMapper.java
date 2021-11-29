package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.api.ResourceQueryPage;
import org.example.entity.Resource;

import java.util.List;

@Mapper
public interface ResourceMapper {
    void addResource(Resource resource);

    void deleteResource(String id);

    void updateResource(Resource resource);

    List<Resource> getResourceList(ResourceQueryPage queryPage);

    Integer getResourceListCount(ResourceQueryPage queryPage);

    List<Resource> getAllResourceList();
}