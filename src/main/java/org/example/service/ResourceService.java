package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.ResourceQueryPage;
import org.example.entity.vo.ResourceVo;

public interface ResourceService {
    Boolean addResource(ResourceVo resourceVo);

    Boolean deleteResource(String id);

    Boolean updateResource(ResourceVo resourceVo);

    Page<ResourceVo> getResourceList(ResourceQueryPage queryPage);
}