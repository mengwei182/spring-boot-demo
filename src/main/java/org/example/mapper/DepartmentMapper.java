package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.Department;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}