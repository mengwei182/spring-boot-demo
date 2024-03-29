package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.result.CommonResult;
import org.example.service.ResourceService;
import org.example.system.entity.vo.ResourceVO;
import org.example.system.query.ResourceQueryPage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
@Api(tags = "资源管理")
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Resource
    private ResourceService resourceService;

    /**
     * 新增资源
     *
     * @param resourceVo
     * @return
     */
    @ApiOperation("新增资源")
    @PostMapping("/add")
    public CommonResult<Boolean> addResource(@RequestBody ResourceVO resourceVo) {
        return CommonResult.success(resourceService.addResource(resourceVo));
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @ApiOperation("删除资源")
    @DeleteMapping("/delete")
    public CommonResult<Boolean> deleteResource(@RequestParam String id) {
        return CommonResult.success(resourceService.deleteResource(id));
    }

    /**
     * 更新资源
     *
     * @param resourceVo
     * @return
     */
    @ApiOperation("更新资源")
    @PutMapping("/update")
    public CommonResult<Boolean> updateResource(@RequestBody ResourceVO resourceVo) {
        return CommonResult.success(resourceService.updateResource(resourceVo));
    }

    /**
     * 分页获取资源列表
     *
     * @param queryPage
     * @return
     */
    @ApiOperation("分页获取资源列表")
    @GetMapping("/list")
    public CommonResult<Page<ResourceVO>> getResourceList(@ModelAttribute ResourceQueryPage queryPage) {
        return CommonResult.success(resourceService.getResourceList(queryPage));
    }

    /**
     * 获取全部资源列表
     *
     * @return
     */
    @ApiOperation("获取全部资源列表")
    @GetMapping("/list/all")
    public CommonResult<List<ResourceVO>> getAllResourceList() {
        return CommonResult.success(resourceService.getAllResourceList());
    }

    /**
     * 根据id获取资源详情
     *
     * @return
     */
    @ApiOperation("根据id获取资源详情")
    @GetMapping("/{id}")
    public CommonResult<ResourceVO> getResourceById(@PathVariable String id) {
        return CommonResult.success(resourceService.getResourceById(id));
    }

    /**
     * 刷新系统中所有资源
     *
     * @return
     */
    @ApiOperation("刷新系统中所有资源")
    @GetMapping("/refresh")
    public CommonResult<?> refreshResource() {
        resourceService.refreshResource();
        return CommonResult.success();
    }
}