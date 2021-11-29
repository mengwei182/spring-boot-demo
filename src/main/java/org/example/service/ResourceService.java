package org.example.service;

import org.example.api.ResourceQueryPage;
import org.example.common.model.CommonResult;
import org.example.entity.Resource;

public interface ResourceService {
    CommonResult addResource(Resource resource);

    CommonResult deleteResource(String id);

    CommonResult updateResource(Resource resource);

    CommonResult getResourceList(ResourceQueryPage queryPage);
}