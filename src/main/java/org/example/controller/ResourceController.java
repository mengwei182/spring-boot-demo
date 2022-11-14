package org.example.controller;

import org.example.api.ResourceQueryPage;
import org.example.model.CommonResult;
import org.example.entity.vo.ResourceVo;
import org.example.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Resource
    private ResourceService resourceService;

    @RequestMapping("/add")
    public CommonResult addResource(@RequestBody ResourceVo resourceVo) {
        return CommonResult.success(resourceService.addResource(resourceVo));
    }

    @RequestMapping("/delete")
    public CommonResult deleteResource(@RequestParam String id) {
        return CommonResult.success(resourceService.deleteResource(id));
    }

    @RequestMapping("/update")
    public CommonResult updateResource(@RequestBody ResourceVo resourceVo) {
        return CommonResult.success(resourceService.updateResource(resourceVo));
    }

    @RequestMapping("/list")
    public CommonResult getResourceList(@ModelAttribute ResourceQueryPage queryPage) {
        return CommonResult.success(resourceService.getResourceList(queryPage));
    }
}