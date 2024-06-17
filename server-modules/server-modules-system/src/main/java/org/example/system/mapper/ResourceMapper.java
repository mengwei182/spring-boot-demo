package org.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.example.system.entity.Resource;
import org.example.system.query.ResourceQueryPage;

import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    List<Resource> getResourceList(IPage<Resource> page, ResourceQueryPage queryPage);
}