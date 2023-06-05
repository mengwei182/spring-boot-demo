package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.api.DictionaryQueryPage;
import org.example.entity.Dictionary;

import java.util.List;

/**
 * @author lihui
 * @since 2023/4/8
 */
@Mapper
public interface DictionaryMapper extends BaseMapper<Dictionary> {
    List<Dictionary> getDictionaries(Page<Dictionary> page, @Param("queryPage") DictionaryQueryPage queryPage);
}