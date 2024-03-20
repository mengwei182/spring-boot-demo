package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.result.CommonResult;
import org.example.service.DictionaryService;
import org.example.system.entity.vo.DictionaryVO;
import org.example.system.query.DictionaryQueryPage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@Api(tags = "字典管理")
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
    @ApiOperation("新增字典")
    @PostMapping("/add")
    public CommonResult<Boolean> addDictionary(@Valid @RequestBody DictionaryVO dictionaryVo) {
        return CommonResult.success(dictionaryService.addDictionary(dictionaryVo));
    }

    /**
     * 删除字典
     *
     * @param id
     * @return
     */
    @ApiOperation("删除字典")
    @DeleteMapping("/delete")
    public CommonResult<Boolean> deleteDictionary(@RequestParam String id) {
        return CommonResult.success(dictionaryService.deleteDictionary(id));
    }

    /**
     * 更新字典
     *
     * @param dictionaryVo
     * @return
     */
    @ApiOperation("更新字典")
    @PutMapping("/update")
    public CommonResult<Boolean> updateDictionary(@Valid @RequestBody DictionaryVO dictionaryVo) {
        return CommonResult.success(dictionaryService.updateDictionary(dictionaryVo));
    }

    /**
     * 查询字典列表
     *
     * @param queryPage
     * @return
     */
    @ApiOperation("查询字典列表")
    @GetMapping("/list")
    public CommonResult<Page<DictionaryVO>> getDictionaryList(@Valid @ModelAttribute DictionaryQueryPage queryPage) {
        return CommonResult.success(dictionaryService.getDictionaryList(queryPage));
    }
}