package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.DictionaryQueryPage;
import org.example.entity.vo.DictionaryVo;

/**
 * @author lihui
 * @since 2023/4/8
 */
public interface DictionaryService {
    /**
     * 新增字典
     *
     * @param dictionaryVo
     * @return
     */
    Boolean addDictionary(DictionaryVo dictionaryVo);

    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    Boolean deleteDictionary(String id);

    /**
     * 更新字典
     *
     * @param dictionaryVo
     * @return
     */
    Boolean updateDictionary(DictionaryVo dictionaryVo);

    /**
     * 查询字典列表
     *
     * @param queryPage
     * @return
     */
    Page<DictionaryVo> getDictionaryList(DictionaryQueryPage queryPage);
}