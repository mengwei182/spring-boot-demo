package org.example.controller;

import org.example.api.DictionaryQueryPage;
import org.example.entity.vo.DictionaryVo;
import org.example.model.CommonResult;
import org.example.service.DictionaryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lihui
 * @since 2023/4/8
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 新增字典
     *
     * @param dictionaryVo
     * @return
     */
    @RequestMapping("/add")
    public CommonResult addDictionary(@Valid @RequestBody DictionaryVo dictionaryVo) {
        return CommonResult.success(dictionaryService.addDictionary(dictionaryVo));
    }

    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public CommonResult deleteDictionary(@RequestParam String id) {
        return CommonResult.success(dictionaryService.deleteDictionary(id));
    }

    /**
     * 更新字典
     *
     * @param dictionaryVo
     * @return
     */
    @RequestMapping("/update")
    public CommonResult updateDictionary(@Valid @RequestBody DictionaryVo dictionaryVo) {
        return CommonResult.success(dictionaryService.updateDictionary(dictionaryVo));
    }

    /**
     * 查询字典列表
     *
     * @param queryPage
     * @return
     */
    @RequestMapping("/list")
    public CommonResult getDictionaryList(@Valid @RequestBody DictionaryQueryPage queryPage) {
        return CommonResult.success(dictionaryService.getDictionaryList(queryPage));
    }
}