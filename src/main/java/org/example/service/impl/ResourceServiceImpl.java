package org.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.ResourceQueryPage;
import org.example.entity.Resource;
import org.example.entity.vo.ResourceVo;
import org.example.mapper.ResourceMapper;
import org.example.service.ResourceService;
import org.example.util.CommonUtils;
import org.example.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @Override
    public Boolean addResource(ResourceVo resourceVo) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceVo, resource);
        resource.setId(CommonUtils.uuid());
        resourceMapper.insert(resource);
        return true;
    }

    @Override
    public Boolean deleteResource(String id) {
        resourceMapper.deleteById(id);
        return true;
    }

    @Override
    public Boolean updateResource(ResourceVo resourceVo) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceVo, resource);
        resourceMapper.updateById(resource);
        return true;
    }

    @Override
    public Page<ResourceVo> getResourceList(ResourceQueryPage queryPage) {
        Page<Resource> page = new Page<>();
        List<Resource> resourceList = resourceMapper.getResourceList(page, queryPage);
        page.setRecords(resourceList);
        return PageUtils.wrap(page, ResourceVo.class);
    }
}