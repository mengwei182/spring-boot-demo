package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.example.api.ResourceQueryPage;
import org.example.entity.system.Resource;

import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    List<Resource> getResourceList(IPage<Resource> page, ResourceQueryPage queryPage);
}