package org.example.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.common.core.domain.BaseEntity;
import org.example.common.core.exception.ExceptionInformation;
import org.example.common.core.util.CommonUtils;
import org.example.common.core.util.PageUtils;
import org.example.system.entity.Dictionary;
import org.example.system.entity.vo.DictionaryVO;
import org.example.system.exception.SystemException;
import org.example.system.mapper.DictionaryMapper;
import org.example.system.query.DictionaryQueryPage;
import org.example.system.service.DictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihui
 * @since 2023/4/8
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {
    @Resource
    private DictionaryMapper dictionaryMapper;

    /**
     * 新增字典
     *
     * @param dictionaryVo
     * @return
     */
    @Override
    public Boolean addDictionary(DictionaryVO dictionaryVo) {
        Dictionary dictionary = new Dictionary();
        BeanUtils.copyProperties(dictionaryVo, dictionary);
        dictionary.setId(CommonUtils.uuid());
        String parentId = BaseEntity.TOP_PARENT_ID;
        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<>();
        // 有父级id
        if (!StrUtil.isEmpty(dictionaryVo.getParentId())) {
            Dictionary parentDictionary = dictionaryMapper.selectOne(queryWrapper.eq(Dictionary::getId, dictionaryVo.getParentId()));
            if (parentDictionary == null) {
                throw new SystemException(ExceptionInformation.SYSTEM_3002.getCode(), ExceptionInformation.SYSTEM_3002.getMessage());
            }
            parentId = dictionaryVo.getParentId();
            dictionary.setIdChain(parentDictionary.getIdChain() + "," + parentDictionary.getId());
        }
        // 无父级id
        if (StrUtil.isEmpty(dictionaryVo.getParentId())) {
            dictionary.setParentId(BaseEntity.TOP_PARENT_ID);
            dictionary.setIdChain(BaseEntity.TOP_PARENT_ID);
        }
        Dictionary resultDictionary = dictionaryMapper.selectOne(queryWrapper.eq(Dictionary::getParentId, parentId).eq(Dictionary::getName, dictionaryVo.getName()));
        if (resultDictionary != null) {
            throw new SystemException(ExceptionInformation.SYSTEM_3013.getCode(), ExceptionInformation.SYSTEM_3013.getMessage());
        }
        // 校验编码唯一性
        resultDictionary = dictionaryMapper.selectOne(queryWrapper.eq(Dictionary::getCode, dictionaryVo.getCode()));
        if (resultDictionary != null) {
            throw new SystemException(ExceptionInformation.SYSTEM_3014.getCode(), ExceptionInformation.SYSTEM_3014.getMessage());
        }
        dictionaryMapper.insert(dictionary);
        return true;
    }

    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteDictionary(String id) {
        Dictionary dictionary = dictionaryMapper.selectById(id);
        if (dictionary == null) {
            throw new SystemException(ExceptionInformation.EXCEPTION_1001.getCode(), ExceptionInformation.EXCEPTION_1001.getMessage());
        }
        List<Dictionary> dictionaries = dictionaryMapper.selectList(new LambdaQueryWrapper<Dictionary>().eq(Dictionary::getParentId, id));
        if (!CollectionUtil.isEmpty(dictionaries)) {
            throw new SystemException(ExceptionInformation.SYSTEM_3015.getCode(), ExceptionInformation.SYSTEM_3015.getMessage());
        }
        dictionaryMapper.deleteById(id);
        return true;
    }

    /**
     * 更新字典
     *
     * @param dictionaryVo
     * @return
     */
    @Override
    public Boolean updateDictionary(DictionaryVO dictionaryVo) {
        String parentId = BaseEntity.TOP_PARENT_ID;
        LambdaQueryWrapper<Dictionary> queryWrapper = new LambdaQueryWrapper<>();
        Dictionary resultDictionary = dictionaryMapper.selectOne(queryWrapper.eq(Dictionary::getParentId, parentId).eq(Dictionary::getName, dictionaryVo.getName()));
        if (resultDictionary != null) {
            throw new SystemException(ExceptionInformation.SYSTEM_3013.getCode(), ExceptionInformation.SYSTEM_3013.getMessage());
        }
        Dictionary dictionary = new Dictionary();
        dictionary.setId(dictionaryVo.getId());
        dictionary.setName(dictionaryVo.getName());
        dictionaryMapper.updateById(dictionary);
        return true;
    }

    /**
     * 查询字典列表
     *
     * @param queryPage
     * @return
     */
    @Override
    public Page<DictionaryVO> getDictionaryList(DictionaryQueryPage queryPage) {
        Page<Dictionary> page = new Page<>(queryPage.getPageNumber(), queryPage.getPageSize());
        List<Dictionary> dictionaries = dictionaryMapper.getDictionaries(page, queryPage);
        page.setRecords(dictionaries);
        return PageUtils.wrap(page, DictionaryVO.class);
    }
}