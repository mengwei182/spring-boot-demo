package org.example.service.impl;

import org.example.api.ResourceQueryPage;
import org.example.common.model.CommonResult;
import org.example.common.model.Page;
import org.example.common.util.CommonUtils;
import org.example.common.util.PageUtils;
import org.example.entity.Resource;
import org.example.mapper.ResourceMapper;
import org.example.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @Override
    public CommonResult addResource(Resource resource) {
        resource.setId(CommonUtils.getUUID());
        resourceMapper.addResource(resource);
        return CommonResult.success();
    }

    @Override
    public CommonResult deleteResource(String id) {
        resourceMapper.deleteResource(id);
        return CommonResult.success();
    }

    @Override
    public CommonResult updateResource(Resource resource) {
        resourceMapper.updateResource(resource);
        return CommonResult.success();
    }

    @Override
    public CommonResult getResourceList(ResourceQueryPage queryPage) {
        List<Resource> resourceList = resourceMapper.getResourceList(queryPage);
        Integer total = resourceMapper.getResourceListCount(queryPage);
        Page page = PageUtils.wrapper(queryPage, resourceList, total);
        return CommonResult.success(page);
    }
}