package org.example.controller;

import org.example.api.ResourceQueryPage;
import org.example.common.model.CommonResult;
import org.example.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ResourceController {
    @Resource
    private ResourceService resourceService;

    @RequestMapping(value = "/resource", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CommonResult addResource(@RequestBody org.example.entity.Resource resource) {
        return resourceService.addResource(resource);
    }

    @RequestMapping(value = "/resource", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
    public CommonResult deleteResource(@RequestParam String id) {
        return resourceService.deleteResource(id);
    }

    @RequestMapping(value = "/resource", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public CommonResult updateResource(@RequestBody org.example.entity.Resource resource) {
        return resourceService.updateResource(resource);
    }

    @RequestMapping(value = "/resource", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public CommonResult getResourceList(@ModelAttribute ResourceQueryPage queryPage) {
        return resourceService.getResourceList(queryPage);
    }
}