package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.ResourceQueryPage;
import org.example.entity.system.vo.ResourceVo;
import org.example.model.CommonResult;
import org.example.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihui
 * @since 2023/4/3
 */
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
    @RequestMapping("/add")
    public CommonResult<Boolean> addResource(@RequestBody ResourceVo resourceVo) {
        return CommonResult.success(resourceService.addResource(resourceVo));
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public CommonResult<Boolean> deleteResource(@RequestParam String id) {
        return CommonResult.success(resourceService.deleteResource(id));
    }

    /**
     * 更新资源
     *
     * @param resourceVo
     * @return
     */
    @RequestMapping("/update")
    public CommonResult<Boolean> updateResource(@RequestBody ResourceVo resourceVo) {
        return CommonResult.success(resourceService.updateResource(resourceVo));
    }

    /**
     * 分页获取资源列表
     *
     * @param queryPage
     * @return
     */
    @RequestMapping("/list")
    public CommonResult<Page<ResourceVo>> getResourceList(@ModelAttribute ResourceQueryPage queryPage) {
        return CommonResult.success(resourceService.getResourceList(queryPage));
    }

    /**
     * 获取全部资源列表
     *
     * @return
     */
    @RequestMapping("/list/all")
    public CommonResult<List<ResourceVo>> getAllResourceList() {
        return CommonResult.success(resourceService.getAllResourceList());
    }

    /**
     * 根据id获取资源详情
     *
     * @return
     */
    @RequestMapping("/{id}")
    public CommonResult<ResourceVo> getResourceById(@PathVariable String id) {
        return CommonResult.success(resourceService.getResourceById(id));
    }

    /**
     * 刷新系统中所有资源
     *
     * @return
     */
    @RequestMapping("/refresh")
    public CommonResult<?> refreshResource() {
        resourceService.refreshResource();
        return CommonResult.success();
    }
}